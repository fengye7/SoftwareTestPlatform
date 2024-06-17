<template>
  <el-card class="visualization">
    <h2>测试情况</h2>
    <div id="pieChart" class="chart"></div>
    <h2>版本迭代</h2>
    <el-card class="iteration" :shadow="never">
      <div id="iterationChart" class="chart"></div>
    </el-card>
    <el-table :data="iterationTable" :border="true" :style="{ width: '100%' }">
      <el-table-column v-for="column in iterationColumns" :key="column.prop" :prop="column.prop"
        :label="column.label"></el-table-column>
    </el-table>
    <el-button type="success" plain @click="downloadCSVChart(
      iterationColumns.map(c => c.label), iterationTable, '版本迭代数据.csv')">下载表格</el-button>
  </el-card>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts/core'
import { LineChart, PieChart, BarChart } from 'echarts/charts'
import { TitleComponent, TooltipComponent, GridComponent, DatasetComponent, TransformComponent, LegendComponent, ToolboxComponent } from 'echarts/components'
import { LabelLayout, UniversalTransition } from 'echarts/features'
import { CanvasRenderer } from 'echarts/renderers'
import { defineProps } from 'vue'
import { downloadCSVChart } from '@/utils/downloadChart'

const props = defineProps({
  pieChartOption: {  // 饼状图
    type: Object,
    required: true
  },
  barChartOption: {  // 柱状图
    type: Object,
    required: true
  },
  tableOption: {  // 表格数据
    type: Object,
    required: true
  },
});

echarts.use([
  LineChart,
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent,
  LabelLayout,
  UniversalTransition,
  CanvasRenderer,
  LegendComponent,
  PieChart,
  ToolboxComponent,
  BarChart
]);

const iterationColumns = ref([]);
const iterationTable = ref([]);

let pieChart = null;
let iterationChart = null;


const initCharts = () => {
  const pieChartContainer = document.getElementById('pieChart');
  const iterationChartContainer = document.getElementById('iterationChart');

  if (pieChartContainer && iterationChartContainer) {
    pieChartContainer.style.width = '100%';
    pieChartContainer.style.height = '400px';
    iterationChartContainer.style.width = '100%';
    iterationChartContainer.style.height = '400px';

    pieChart = echarts.init(pieChartContainer);
    iterationChart = echarts.init(iterationChartContainer);

    // 设置初始选项
    pieChart.setOption(props.pieChartOption);
    iterationChart.setOption(props.barChartOption);

    // 设置表格
    iterationColumns.value = props.tableOption.columns;
    iterationTable.value = props.tableOption.data;
  } else {
    console.error('Chart containers not found')
  }
}

const updatePieChart = () => {
  if (pieChart) {
    pieChart.setOption(props.pieChartOption);
  }
};
const updateBarChart = () => {
  if (iterationChart) {
    iterationChart.setOption(props.barChartOption);
  }
};
const updateTable = () => {
  iterationColumns.value = props.tableOption.columns;
  iterationTable.value = props.tableOption.data;
};

onMounted(initCharts);

// 在vue生命周期中子组件创建一次后只会赋值一次，之后父组件数值变化了，子组件数值也变化，
// 但显示的数据不会发生改变。
// 所以需要 watch 来重新赋值
watch(() => props.pieChartOption, updatePieChart, { deep: true });
watch(() => props.barChartOption, updateBarChart, { deep: true });
watch(() => props.tableOption, updateTable, { deep: true });
</script>

<style scoped>
.visualization {
  width: 100%;
  height: 100%;
  padding: 20px;
  box-sizing: border-box;
  overflow-y: auto;
  overflow-x: auto;
}

.chart {
  font-size: 1rem;
  width: 100%;
  height: 400px;
  /* Ensure the container has enough height */
}

.iteration {
  box-sizing: border-box;
  width: 100%;
  margin-bottom: 20px;
}

.el-button {
  margin-top: 10px;
}
</style>
