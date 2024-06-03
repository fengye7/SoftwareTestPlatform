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
      <pre><code class="javascript" v-html="formattedTestCode"></code></pre>
    </div>
  </el-card>
</template>

<script setup>
import { ref, computed, onMounted, reactive } from "vue";
import { useStore } from "vuex";
import axios from "axios";
import hljs from "highlight.js";
import "highlight.js/styles/github.css"; // 选择一个喜欢的样式

// 使用 Vuex 的 store
const store = useStore();
// 获取项目名称
const projectName = computed(() => store.state.project.name);
const baseURL = process.env.VUE_APP_API_FileServer_URL;
const databaseURL = process.env.VUE_APP_API_DatabaseServer_URL;

// 项目信息
const projectInfo = reactive({
  name: "",
  sketch:"",
  thinking:"",
  description: "",
  date: "",
  manager: "",
  resource: ""
});
// 代码信息
const scriptCode = ref("");

// 获取项目详细信息的函数
const fetchProjectInfo = async () => {
  try {
    const response = await axios.get(
      `${databaseURL}/projects/project-details?name=${projectName.value}`
    );
    // 更新 projectInfo
    console.log(response.data);

    Object.assign(projectInfo, response.data);
    console.log(projectInfo);
  } catch (error) {
    console.error("Error fetching project info:", error);
  }
};

// 在组件挂载时获取数据
onMounted(() => {
  highlightCode();
  fetchProjectInfo();
});

// 高亮代码
const highlightCode = () => {
  const codeElement = document.querySelector("pre code");
  if (codeElement) {
    hljs.highlightElement(codeElement);
  }
};

// 获取项目详细信息的函数
const fetchScriptCode = async () => {
  try {
    const response = await axios.get(
      `${baseURL}/files/scriptContent?projectName=判断三角形&scriptName=triangleJudgev0.1.0.js`
    );
    scriptCode.value = response.data;
  } catch (error) {
    console.error("Error fetching scriptCode:", error);
  }
};

// 计算属性，用于格式化测试代码
const formattedTestCode = computed(() => {
  fetchScriptCode();
  if (scriptCode.value) {
    return scriptCode.value.replace(/</g, "&lt;").replace(/>/g, "&gt;");
  }
  return "";
});
</script>

<style scoped>
.el-card {
  width: 100%;
  height: 100%;
  padding: 20px;
  box-sizing: border-box;
  background-color: rgb(245, 241, 248);
}

.project-description,
.test-ideas,
.test-code {
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
</style>
