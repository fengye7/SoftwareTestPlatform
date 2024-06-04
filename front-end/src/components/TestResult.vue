<template>
  <div class="result">
    <n-scrollbar style="max-height: 78vh;">
      <n-h2>测试结果</n-h2>
        <n-data-table size="small" :bordered="false" :columns="resultColumns" :data="resultData"></n-data-table>
    </n-scrollbar>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { NScrollbar, NH2, NDataTable } from 'naive-ui'
import axios from "axios";

const baseURL = process.env.VUE_APP_API_FileServer_URL;

const resultColumns = ref([]);
const resultData = ref([]);

const fetchTestResultTableData = () => {
  axios.get(`${baseURL}/`)  // 根据实际后端 API 调整 URL
    .then(response => {
      console.log(response.data);
      resultData.value = [
        { testCaseID: 1, expectedOutput: 0, actualOutput: 1, correctness: 'FALSE', time: '24-06-01' },
        { testCaseID: 2, expectedOutput: 9, actualOutput: 9, correctness: 'TRUE', time: '24-06-01' },
        { testCaseID: 3, expectedOutput: 99, actualOutput: 90, correctness: 'FALSE', time: '24-06-01' }
      ];
      resultColumns.value = [
        { title: 'testCaseID', key: 'testCaseID' },
        { title: 'expectedOutput', key: 'expectedOutput' },
        { title: 'actualOutput', key: 'actualOutput' },
        { title: 'correctness', key: 'correctness' },
        { title: 'time', key: 'time' }
      ];
    })
}

onMounted(fetchTestResultTableData)
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
