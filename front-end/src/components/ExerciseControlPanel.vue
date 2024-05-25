<template>
  <div class="control-panel">
    <h2>控制面板</h2>
    
    <!-- 选择测试代码版本 -->
    <div class="form-group">
      <label for="codeVersion">选择测试代码版本:</label>
      <select id="codeVersion" v-model="selectedCodeVersion">
        <option v-for="version in codeVersions" :key="version" :value="version">{{ version }}</option>
      </select>
    </div>
    
    <!-- 选择新建测试代码传入存储 -->
    <div class="form-group">
      <label for="newCode">添加测试代码到后台（js文件）:</label>
      <input type="file" id="newCode" @change="handleNewCode" />
      <button class="upload-button" @click="uploadNewCode">上传测试代码</button>
    </div>
    
    <!-- 选择测试集 -->
    <div class="form-group">
      <label for="testSet">选择测试集:</label>
      <select id="testSet" v-model="selectedTestSet">
        <option v-for="testSet in testSets" :key="testSet" :value="testSet">{{ testSet }}</option>
      </select>
    </div>
    
    <!-- 选择传入新的测试集文件（csv格式） -->
    <div class="form-group">
      <label for="newTestSet">添加测试集文件到后台（csv格式）:</label>
      <input type="file" id="newTestSet" @change="handleNewTestSet" accept=".csv" />
      <button class="upload-button" @click="uploadNewTestSet">上传测试集文件</button>
    </div>
    
    <!-- 开始测试按钮启动测试 -->
    <div class="form-group">
      <button class="start-button" @click="startTesting">开始测试</button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useStore } from 'vuex';
import axios from 'axios';

// 访问store
const store = useStore();
const projectName = computed(() => store.state.project.name);

const selectedCodeVersion = ref('');
const selectedTestSet = ref('');

const codeVersions = ref([]);
const testSets = ref([]);

const newCodeFile = ref(null);
const newTestSetFile = ref(null);

onMounted(() => {
  loadFiles();
});

const loadFiles = async () => {
  try {
    const scriptsResponse = await axios.get(`http://localhost:8081/files/scripts?projectName=${projectName.value}`);
    const testsResponse = await axios.get(`http://localhost:8081/files/tests?projectName=${projectName.value}`);
    codeVersions.value = scriptsResponse.data;
    testSets.value = testsResponse.data;
  } catch (error) {
    console.error("Error loading files:", error);
  }
};

const handleNewCode = (event) => {
  const file = event.target.files[0];
  if (file && file.name.endsWith('.js')) {
    newCodeFile.value = file;
  } else {
    alert("请选择一个有效的JS文件");
    newCodeFile.value = null;
  }
};

const handleNewTestSet = (event) => {
  const file = event.target.files[0];
  if (file && file.name.endsWith('.csv')) {
    newTestSetFile.value = file;
  } else {
    alert("请选择一个有效的CSV文件");
    newTestSetFile.value = null;
  }
};

const uploadNewCode = async () => {
  if (newCodeFile.value) {
    const formData = new FormData();
    formData.append('projectName', projectName.value);
    formData.append('file', newCodeFile.value);

    try {
      await axios.post('http://localhost:8081/files/uploadScript', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      loadFiles(); // 重新加载文件以更新列表
    } catch (error) {
      console.error("Error uploading new code:", error);
    }
  } else {
    alert("请先选择一个JS文件");
  }
};

const uploadNewTestSet = async () => {
  if (newTestSetFile.value) {
    const formData = new FormData();
    formData.append('projectName', projectName.value);
    formData.append('file', newTestSetFile.value);

    try {
      await axios.post('http://localhost:8081/files/uploadTestSet', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      loadFiles(); // 重新加载文件以更新列表
    } catch (error) {
      console.error("Error uploading new test set:", error);
    }
  } else {
    alert("请先选择一个CSV文件");
  }
};

const startTesting = () => {
  if (!selectedCodeVersion.value || !selectedTestSet.value) {
    alert("请先选择测试脚本和测试集");
    return;
  }
  console.log("Testing started with:", {
    selectedCodeVersion: selectedCodeVersion.value,
    selectedTestSet: selectedTestSet.value,
  });
  alert(
    `开始测试：\n脚本版本：${selectedCodeVersion.value}\n测试集：${selectedTestSet.value}`
  );
};
</script>

<style scoped>
.control-panel {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #f9fcef;
  padding: 20px;
  border-radius: 15px;
  box-shadow: 5px 10px 20px rgba(0, 0, 0, 0.1);
  height: 85%;
  width: 85%;
  margin: auto;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group select,
.form-group input[type="file"] {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  align-self: center;
}

.upload-button {
  background-color: #e0e0e0;
  color: #000;
  font-size: 14px;
  padding: 6px 12px;
  margin-top: 10px;
}

.upload-button:hover {
  background-color: #d0d0d0;
}

.start-button {
  background-color: #409eff;
  color: #fff;
  font-size: 16px;
  padding: 10px 20px;
}

.start-button:hover {
  background-color: #66b1ff;
}
</style>
