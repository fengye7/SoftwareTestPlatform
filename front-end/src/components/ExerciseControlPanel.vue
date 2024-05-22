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
      <label for="newCode">选择新建测试代码:</label>
      <input type="file" id="newCode" @change="uploadNewCode" />
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
      <label for="newTestSet">选择新的测试集文件（csv格式）:</label>
      <input type="file" id="newTestSet" @change="uploadNewTestSet" accept=".csv" />
    </div>
    
    <!-- 开始测试按钮启动测试 -->
    <div class="form-group">
      <button @click="startTesting">开始测试</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const selectedCodeVersion = ref('');
const selectedTestSet = ref('');

const codeVersions = ref(['v1.0', 'v1.1', 'v2.0']); // Example versions
const testSets = ref(['Test Set 1', 'Test Set 2', 'Test Set 3']); // Example test sets

const uploadNewCode = (event) => {
  const file = event.target.files[0];
  if (file) {
    // Handle file upload logic here
    console.log('New code file uploaded:', file.name);
  }
};

const uploadNewTestSet = (event) => {
  const file = event.target.files[0];
  if (file) {
    // Handle file upload logic here
    console.log('New test set file uploaded:', file.name);
  }
};

const startTesting = () => {
  // Start testing logic here
  console.log('Testing started with:', {
    selectedCodeVersion: selectedCodeVersion.value,
    selectedTestSet: selectedTestSet.value
  });
};
</script>

<style scoped>
.control-panel {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  height: 100%;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
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
  background-color: #409eff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  align-self: center;
}

button:hover {
  background-color: #66b1ff;
}
</style>
