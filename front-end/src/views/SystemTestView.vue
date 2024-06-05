<template>
  <div class="console-container">
    <div class="left-card">
      <!--描述信息-->
      <h3>系统测试信息</h3>
      <p class="text-p">使用框架: Spring Boot, JUnit</p>
      <p class="text-p">
        实现方式: 使用 Maven 执行系统测试，实时通过 WebSocket 输出测试信息。
      </p>
      <p class="text-p">
        提供两次报告对比功能，请在右侧执行测试后点击“获取最新测试结果”按钮获得最新的测试报告，然后点击“下载测试报告”按钮下载报告。
      </p>
      <h6>上次测试报告</h6>
      <pre>{{ lastTestReport }}</pre>
      <el-divider></el-divider>
      <!--测试结果-->
      <el-button
        v-if="isTestSuccess == '测试成功'"
        @click="fetchTestReport"
        type="primary"
        >获取最新测试结果</el-button
      >
      <p v-else-if="isTestSuccess == '等待测试'" class="info-p">请先进行测试</p>
      <p v-else-if="isTestSuccess == '测试失败'" class="info-p">测试失败.未知错误</p>
      <!--下载测试报告-->
      <div v-if="testReport" class="test-report">
        <pre>{{ testReport }}</pre>
        <el-button @click="downloadTestReport" type="primary"
          >下载测试报告</el-button
        >
      </div>
    </div>
    <div class="right-card">
      <h3>测试面板</h3>
      <div class="button-container">
        <div class="left-button-group">
          <el-button @click="runSystemTest" type="primary"
            >运行系统测试</el-button
          >
        </div>
        <div class="right-button-group">
          <el-button @click="terminateSystemTest" type="danger">终止系统测试</el-button>
          <el-button @click="clearTestInfo" type="info">清除测试信息</el-button>
        </div>
      </div>
      <el-divider></el-divider>
      <h4>测试信息</h4>
      <el-card class="console-output" id="scrollArea">
        <pre>{{ limitedOutput }}</pre>
        <el-button @click="downloadFullOutput" type="info"
          >下载完整输出</el-button
        >
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";
import { ElMessage } from "element-plus";
import "element-plus/es/components/message/style/css";

// 确保环境变量包含协议部分，如 'http://localhost:8090' 和 'ws://localhost:8090'
const baseURL =
  process.env.VUE_APP_API_ProjectTestServer_URL || "http://localhost:8090";
const webSocketURL =
  process.env.VUE_APP_API_WebSocket_URL || "ws://localhost:8090";

// 同步显示的测试输出
const output = ref("");
const limitedOutput = ref("");
const testReport = ref("");
const lastTestReport = ref("");
const isTestSuccess = ref("等待测试");
let socket = null;

// 初始化上次的测试报告
onMounted(async () => {
  try {
    const response = await fetch(`${baseURL}/get-system-test-report`);
    const result = await response.text();
    lastTestReport.value = result;
    // console.log("上次的测试报告已获取");
  } catch (error) {
    lastTestReport.value = "获取测试报告时出错: " + error.message;
    // console.error("获取测试报告时出错:", error.message);
  }
});

const runSystemTest = async () => {
//   console.log("Starting system test...");

  socket = new WebSocket(`${webSocketURL}/test-output`);

  socket.onmessage = (event) => {
    // console.log("Received message:", event.data);
    output.value += event.data + "\n";
    updateLimitedOutput();
    // 自动滚动到最新信息
    nextTick(() => {
      let ele = document.getElementById("scrollArea");
      if (ele) {
        ele.scrollTop = ele.scrollHeight;
      }
    });
  };

  socket.onopen = async () => {
    // console.log("WebSocket connection opened.");
    try {
      const response = await fetch(`${baseURL}/run-system-test`);
      const result = await response.text();
      ElMessage.success(result);
      isTestSuccess.value = "测试成功";
    //   console.log("System test started successfully.");
    } catch (error) {
      isTestSuccess.value = "测试失败";
      ElMessage.error("Error running tests: " + error.message);
    //   console.error("Error running tests:", error.message);
    }
  };

  socket.onclose = () => {
    ElMessage.info("WebSocket connection closed.");
    // console.log("WebSocket connection closed.");
  };

  socket.onerror = (error) => {
    ElMessage.error("WebSocket error: " + error.message);
    // console.error("WebSocket error:", error.message);
  };
};

// 更新 limitedOutput 以显示最后 150 行
const updateLimitedOutput = () => {
  const lines = output.value.split("\n");
  if (lines.length > 150) {
    limitedOutput.value = lines.slice(-150).join("\n");
  } else {
    limitedOutput.value = output.value;
  }
};

// 终止测试
const terminateSystemTest = async () => {
//   console.log("Terminating system test...");
  try {
    const response = await fetch(`${baseURL}/terminate-test`, {
      method: "POST",
    });
    const result = await response.text();
    ElMessage.success(result);
    // console.log("System test terminated successfully.");
    if (socket) {
      socket.close();
    }
  } catch (error) {
    ElMessage.error("Error terminating tests: " + error.message);
    // console.error("Error terminating tests:", error.message);
  }
};

// 清除测试信息
const clearTestInfo = () => {
  output.value = "";
  limitedOutput.value = "";
  testReport.value = "";
  isTestSuccess.value = "等待测试";
  ElMessage.info("测试信息已清除");
};

const fetchTestReport = async () => {
  try {
    const response = await fetch(`${baseURL}/get-system-test-report`);
    const result = await response.text();
    testReport.value = result;
    ElMessage.success("测试报告已获取");
  } catch (error) {
    ElMessage.error("获取测试报告时出错: " + error.message);
    // console.error("获取测试报告时出错:", error.message);
  }
};

const downloadTestReport = () => {
  const blob = new Blob([testReport.value], { type: "text/plain" });
  const url = window.URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = "system-test-report.txt";
  document.body.appendChild(a);
  a.click();
  window.URL.revokeObjectURL(url);
  document.body.removeChild(a);
};

const downloadFullOutput = () => {
  const blob = new Blob([output.value], { type: "text/plain" });
  const url = window.URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = "full-output.txt";
  document.body.appendChild(a);
  a.click();
  window.URL.revokeObjectURL(url);
  document.body.removeChild(a);
};
</script>

<style scoped>
.console-container {
  display: flex;
  padding: 20px;
  gap: 20px;
  background-color: rgb(245, 224, 224);
}

.left-card,
.right-card {
  flex: 1;
  background-color: rgb(249, 248, 230);
  border: 5px solid #cccccc;
  border-radius: 15px;
  box-shadow: 5px 10px 20px rgba(0, 0, 0, 0.1);
  height: 85vh;
  width: 100%;
  padding: 20px;
}
.left-card {
  overflow-y: auto;
}

.left-card:hover,
.right-card:hover {
  animation: card-shake 0.1s ease-in-out;
}

.console-output {
  padding: 20px;
  margin: 20px;
  background-color: #1e1e1e;
  color: #d4d4d4;
  height: 550px;
  width: 40vw;
  overflow-y: auto;
  font-family: "Courier New", Courier, monospace;
  border-radius: 15px;
  box-shadow: 5px 10px 20px rgba(0, 0, 0, 0.1);
}

pre {
  white-space: pre-wrap;
}

.text-p {
    color: rgb(30, 25, 109);
    font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    font-size: 15px;
}
.info-p{
    font-family: 'fengye7Font';
    color: rgb(88, 15, 140);
    font-size: 25px;
}

.button-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px; /* 与上面的内容保持一定距离 */
  width: 100%;
}

.left-button-group {
  display: flex;
}

.right-button-group {
  display: flex;
  gap: 10px; /* 按钮之间的间距 */
}

@keyframes card-shake {
  /* 垂直抖动，核心代码 */
  0% {
    transform: translateX(0);
  }
  25% {
    transform: translateX(-5px);
  }
  50% {
    transform: translateX(5px);
  }
  75% {
    transform: translateX(-5px);
  }
  100% {
    transform: translateX(0);
  }
}
</style>