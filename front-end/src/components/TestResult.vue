<template>
  <div class="result">
    <n-scrollbar style="max-height: 78vh;">
      <n-h2>测试结果</n-h2>
      <n-data-table
        size="small"
        :bordered="true"
        :columns="resultColumns"
        :data="resultData"
        :editable="true"
        @update:data="handleDataUpdate"
      ></n-data-table>
      <n-button @click="downloadExcel">下载 Excel</n-button>
      <n-h2>测试统计</n-h2>
      <n-data-table
        size="small"
        :bordered="true"
        :columns="summaryColumns"
        :data="[summaryData]"
        :editable="true"
      ></n-data-table>
      <n-button @click="storeTestResults">存储测试结果</n-button>
    </n-scrollbar>
  </div>
</template>

<script setup>
import { ref, watch, defineProps, h, computed, watchEffect } from 'vue'
import { useStore } from 'vuex'
import { NScrollbar, NH2, NDataTable, NButton, NInput } from 'naive-ui'
import * as XLSX from 'xlsx'
import { saveAs } from 'file-saver'
import axios from 'axios'

const store = useStore();
const scriptName = computed(() => store.state.exerciseTest.scriptName);
const testSetName = computed(() => store.state.exerciseTest.testSetName);

const databaseURL = process.env.VUE_APP_API_DatabaseServer_URL;

const props = defineProps({
  responseData: {
    type: Array,
    required: true
  }
});

const resultColumns = ref([
  { title: 'testCaseID', key: 'testCaseID' },
  { title: 'expectedOutput', key: 'expectedOutput' },
  { title: 'actualOutput', key: 'actualOutput' },
  { title: 'correctness', key: 'correctness' },
  { title: 'time', key: 'time' }
]);

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

const summaryColumns = ref([
  { title: '测试代码', key: 'testCode' },
  { title: '测试集', key: 'testSet' },
  { title: '正确数', key: 'correctCount' },
  { title: '总数', key: 'totalCount' },
  { title: '缺陷描述', key: 'defectDescription', editable: true, render: (row) => h(NInput, {
    value: row.defectDescription,
    onUpdateValue: (value) => row.defectDescription = value
  }) },
  { title: '描述人', key: 'describer', editable: true, render: (row) => h(NInput, {
    value: row.describer,
    onUpdateValue: (value) => row.describer = value
  }) }
]);

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

const downloadExcel = () => {
  const data = resultData.value.map(item => ({
    '测试用例 ID': item.testCaseID,
    '预期输出': item.expectedOutput,
    '实际输出': item.actualOutput,
    '正确性': item.correctness,
    '时间': item.time
  }));
  const ws = XLSX.utils.json_to_sheet(data);
  const wb = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, ws, "测试结果");
  const wbout = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
  saveAs(new Blob([wbout], { type: "application/octet-stream" }), '测试结果.xlsx');
};

// 存储测试结果
const storeTestResults = async () => {
  try {
    await axios.post(`${databaseURL}/test-results`, {
      testCode: summaryData.value.testCode,
      testSet: summaryData.value.testSet,
      correctCount: summaryData.value.correctCount,
      totalCount: summaryData.value.totalCount,
      defectDescription: summaryData.value.defectDescription,
      describer: summaryData.value.describer
    });
    alert('测试结果已存储');
  } catch (error) {
    console.error('Error storing test results:', error);
    alert('存储测试结果时出错，请稍后重试');
  }
};
</script>

<style scoped>
.result {
  width: 90%;
  box-sizing: border-box;
  min-height: 83vh;
  max-height: 85vh;
}

.n-card {
  margin-bottom: 20px;
}
</style>