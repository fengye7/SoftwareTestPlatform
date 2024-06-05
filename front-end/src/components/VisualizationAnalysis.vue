<template>
  <EChartsWrapper
    :pieChartOption="pieChartOptionData"
    :barChartOption="barChartOptionData"
    :tableOption="tableOptionData"
  />
</template>

<script setup>
import { ref, onMounted, watch, defineProps, computed } from "vue";
import { useStore } from "vuex";
import axios from "axios";
import EChartsWrapper from "@/components/EChartsWrapper.vue";

const baseURL = process.env.VUE_APP_API_DatabaseServer_URL;
const store = useStore();
const testSetName = computed(() => store.state.exerciseTest.testSetName);

// 定义传入的参数
const props = defineProps({
  responseData: {
    type: Array,
    required: true,
  },
});

// 饼状图数据
const pieChartOptionData = ref({
  tooltip: {
    trigger: "item",
  },
  legend: {
    top: "5%",
    left: "center",
  },
  toolbox: {
    feature: {
      saveAsImage: {},
      dataView: { readOnly: false },
    },
  },
  series: [
    {
      name: "Access From",
      type: "pie",
      radius: ["0%", "70%"],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: "#fff",
        borderWidth: 2,
      },
      label: {
        show: false,
        position: "center",
      },
      emphasis: {
        label: {
          show: true,
          fontSize: "40",
          fontWeight: "bold",
        },
      },
      labelLine: {
        show: false,
      },
      data: [],
    },
  ],
});

// 柱状图数据
const barChartOptionData = ref({
  tooltip: {},
  toolbox: {
    feature: {
      saveAsImage: {},
      dataView: { readOnly: false },
      restore: {},
      magicType: { type: ["line", "bar"] },
    },
  },
  xAxis: {
    data: [],
  },
  yAxis: {},
  series: [
    {
      name: "Iterations",
      type: "bar",
      data: [],
    },
  ],
});

// 底部表格数据
const tableOptionData = ref({
  columns: [],
  data: [],
});

const resultData = ref([]);

// 计算正确数量和总数量
const correctCount = computed(() => {
  return resultData.value.filter((item) => item.correctness === "TRUE").length;
});

const totalCount = computed(() => {
  return resultData.value.length;
});

// 更新饼状图数据
const updatePieChartData = () => {
  pieChartOptionData.value.series[0].data = [
    { value: correctCount.value, name: "通过" },
    { value: totalCount.value - correctCount.value, name: "未通过" },
  ];
};

// 更新柱状图数据
const updateBarChartData = (testHistory) => {
  const versions = testHistory.map((item) => item.testCode);
  const correctCounts = testHistory.map((item) => item.correctCount);

  barChartOptionData.value.xAxis.data = versions;
  barChartOptionData.value.series[0].data = correctCounts;
};

// 更新表格数据
const updateTableData = (testHistory) => {
  const tableData = testHistory.map((item) => ({
    version: item.testCode,
    dataset: item.testSet,
    result: `${item.correctCount}/${item.totalCount}`,
    defectDescription: item.defectDescription,
  }));

  tableOptionData.value.data = tableData;
  tableOptionData.value.columns = [
    { title: "版本", key: "version" },
    { title: "测试数据集", key: "dataset" },
    { title: "测试结果", key: "result" },
    { title: "缺陷描述", key: "defectDescription" },
  ];
};

// 获取测试历史数据
const fetchTestHistory = () => {
  axios
    .get(`${baseURL}/test-results/get-by-testSet?testSet=${testSetName.value}`) // 根据实际后端 API 调整 URL
    .then((response) => {
      updateBarChartData(response.data);
      updateTableData(response.data);
    })
    .catch((error) => {
      console.error("Failed to fetch test history:", error);
    });
};

// 监听传递过来的测试结果变化
watch(
  () => props.responseData,
  (newVal) => {
    resultData.value = newVal;
    updatePieChartData();
  },
  { immediate: true }
);

// 监听 store 中 testSetName 的变化
watch(
  () => testSetName.value,
  (newVal) => {
    if (newVal) {
      fetchTestHistory();
    }
  },
  { immediate: true }
);

// 当组件挂载时调用数据获取函数
onMounted(() => {
  updatePieChartData();
  fetchTestHistory();
});
</script>