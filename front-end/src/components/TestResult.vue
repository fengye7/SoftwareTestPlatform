<template>
  <el-card>
    <div class="test-result">
      <h2>测试结果</h2>
      <el-table :border="true" :data="resultData" @update:data="handleDataUpdate">
        <el-table-column v-for="header in resultHeaders" :key="header" :prop="header" :label="header"></el-table-column>
      </el-table>
      <el-button type="success" plain @click="downloadCSVChart(
        resultHeaders, resultData, `testResult_${scriptName}_${testSetName}.csv`)">下载表格</el-button>
    </div>
    <div class="test-count">
      <h2>测试统计</h2>
      <el-table :border="true" :data="[summaryData]" @update:data="handleDataUpdate">
        <el-table-column v-for="header in summaryHeaders" :key="header" :prop="header" :label="header">
          <template v-slot="scope" v-if="header === 'defectDescription' || header === 'describer'">
            <el-input v-model="scope.row[header]" />
          </template>
        </el-table-column>
      </el-table>
      <el-button type="primary" plain @click="storeTestResults">存储测试结果</el-button>
    </div>
  </el-card>
</template>

<script setup>
import { ref, watch, defineProps, computed, watchEffect } from 'vue'
import { useStore } from 'vuex'
import axios from 'axios'
import { downloadCSVChart } from '@/utils/downloadChart'

const store = useStore();
const scriptName = computed(() => store.state.exerciseTest.scriptName);
const testSetName = computed(() => store.state.exerciseTest.testSetName);

const databaseURL = process.env.VUE_APP_API_DatabaseServer_URL;
const projectName = computed(() => store.state.project.name);

const props = defineProps({
  responseData: {
    type: Array,
    required: true
  }
});

const resultData = ref([]);

watch(() => props.responseData, (newVal) => {
  resultData.value = newVal;
}, { immediate: true });

const handleDataUpdate = (newData) => {
  resultData.value = newData;
};

const correctCount = computed(() => {
  return resultData.value.filter(item => item.correctness === 'TRUE').length;
});

const totalCount = computed(() => {
  return resultData.value.length;
});

const summaryData = ref({
  testCode: scriptName.value,
  testSet: testSetName.value,
  correctCount: correctCount.value,
  totalCount: totalCount.value,
  defectDescription: '',
  describer: ''
});

watchEffect(() => {
  summaryData.value.correctCount = correctCount.value;
  summaryData.value.totalCount = totalCount.value;
  summaryData.value.testCode = scriptName.value;
  summaryData.value.testSet = testSetName.value;
});

const resultHeaders = ['testCaseID', 'expectedOutput', 'actualOutput', 'correctness', 'time'];
const summaryHeaders = ['testCode', 'testSet', 'correctCount', 'totalCount', 'defectDescription', 'describer'];

const storeTestResults = async () => {
  try {
    await axios.post(`${databaseURL}/test-results`, {
      testCode: summaryData.value.testCode,
      testSet: summaryData.value.testSet,
      correctCount: summaryData.value.correctCount,
      totalCount: summaryData.value.totalCount,
      defectDescription: summaryData.value.defectDescription,
      describer: summaryData.value.describer,
      projectName: projectName.value,
    });
    alert('测试结果已存储');
  } catch (error) {
    console.error('Error storing test results:', error);
    alert('存储测试结果时出错，请稍后重试');
  }
};
</script>

<style scoped>
.el-card {
  width: 100%;
  height: 100%;
  padding: 20px;
  box-sizing: border-box;
  overflow-y: auto;
  overflow-x: auto;
}

.test-result .test-count {
  margin-top: 20px;
}

.el-button {
  margin-top: 10px;
}
</style>
