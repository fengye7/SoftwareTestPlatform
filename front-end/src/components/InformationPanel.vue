<template>
  <el-card>
    <template #header>
      <h1>{{ projectName }}</h1>
    </template>

    <el-tag type="info">Create: {{ projectInfo.date }}</el-tag>
    <el-tag>{{ projectInfo.manager }}</el-tag>

    <div class="project-description">
      <h2>项目简述</h2>
      <p>{{ projectInfo.sketch }}</p>
    </div>

    <div class="test-ideas">
      <h2>测试思路</h2>
      <p>{{ projectInfo.thinking }}</p>
    </div>

    <div class="test-code">
      <h2>测试代码</h2>
      <pre v-if="scriptCode" style="overflow-x: auto; font-size: 16px;"><code class="javascript" v-html="formattedTestCode"></code></pre>
      <pre v-else>未选择代码</pre>
    </div>

    <div class="test-dataset">
      <h2>测试集</h2>
      <el-table :data="chartData" style="overflow-x: auto;">
        <el-table-column v-for="header in headers" :key="header" :prop="header" :label="header"></el-table-column>
      </el-table>
      <el-button type="success" plain @click="downloadCSVChart(
        headers, chartData, testSetName)">下载测试数据集</el-button>
    </div>
  </el-card>
</template>

<script setup>
import { ref, computed, onMounted, reactive, watch } from "vue";
import { useStore } from "vuex";
import axios from "axios";
import highlight from "highlight.js";
import "highlight.js/styles/github.css"; // 选择一个喜欢的样式
import { ElMessage } from "element-plus";
import { downloadCSVChart } from '@/utils/downloadChart'

// 使用 Vuex 的 store
const store = useStore();
// 获取项目名称
const projectName = computed(() => store.state.project.name);
const scriptName = computed(() => store.state.exerciseTest.scriptName);
const testSetName = computed(() => store.state.exerciseTest.testSetName);
const baseURL = process.env.VUE_APP_API_FileServer_URL;
const databaseURL = process.env.VUE_APP_API_DatabaseServer_URL;

// 项目信息
const projectInfo = reactive({
  name: "",
  sketch: "",
  thinking: "",
  description: "",
  date: "",
  manager: "",
  resource: "",
});
// 代码信息
const scriptCode = ref("");

// 获取项目详细信息的函数
const fetchProjectInfo = async () => {
  try {
    const response = await axios.get(
      `${databaseURL}/projects/project-details?name=${projectName.value}`
    );
    Object.assign(projectInfo, response.data);
  } catch (error) {
    ElMessage.error("Error fetching project info:", error);
  }
};

// 在组件挂载时获取数据
onMounted(() => {
  fetchProjectInfo();
  if (scriptName.value) {
    fetchScriptCode();
  }
  if (testSetName.value) {
    fetchTestSet();
  }
});

// 获取项目详细信息的函数
const fetchScriptCode = async () => {
  try {
    const response = await axios.get(
      `${baseURL}/files/scriptContent?projectName=${projectName.value}&scriptName=${scriptName.value}`
    );
    scriptCode.value = response.data;
  } catch (error) {
    console.error("Error fetching scriptCode:", error);
  }
};

// 格式化和高亮测试代码
const formattedTestCode = computed(() => {
  if (scriptCode.value) {
    scriptCode.value.replace(/</g, "&lt;").replace(/>/g, "&gt;");
  }
  const { value } = highlight.highlight('js', scriptCode.value)
  return value;
});

// 监视 scriptName 的变化
watch(scriptName, (newVal) => {
  if (newVal) {
    fetchScriptCode();
  }
});

// 图表相关
const headers = ref([]);
const chartData = ref([]);

// 解析 CSV 数据
const parseCSV = (csv) => {
  const lines = csv.split("\n");
  const result = [];
  headers.value = lines[0].split(",");

  for (let i = 1; i < lines.length; i++) {
    const obj = {};
    const currentline = lines[i].split(",");
    for (let j = 0; j < headers.value.length; j++) {
      obj[headers.value[j]] = currentline[j];
    }
    result.push(obj);
  }
  return result;
};

// 获取测试集数据的函数
const fetchTestSet = async () => {
  try {
    const response = await axios.get(
      `${baseURL}/files/testSetContent?projectName=${projectName.value}&testSetName=${testSetName.value}`
    );
    chartData.value = parseCSV(response.data);
    console.log(chartData.value);
  } catch (error) {
    console.error("Error fetching dataset:", error);
  }
};

// 监视 testSetName 的变化
watch(testSetName, (newVal) => {
  if (newVal) {
    fetchTestSet();
  }
});


</script>

<style scoped>
.el-card {
  width: 100%;
  height: 100%;
  padding: 20px;
  box-sizing: border-box;
  background-color: rgb(245, 241, 248);
  overflow-y: auto;
  overflow-x: auto;
}

.project-description,
.test-ideas,
.test-code,
.test-dataset {
  margin-top: 20px;
}

h1 {
  margin: 0;
}

h2 {
  margin-top: 0;
  margin-bottom: 10px;
}

p {
  margin: 0;
}

pre {
  background: #f6f8fa;
  padding: 10px;
  border-radius: 5px;
  overflow-x: auto;
}

.chart {
  width: 100%;
  height: 400px;
  margin-top: 20px;
}

.el-button {
  margin-top: 10px;
}
</style>