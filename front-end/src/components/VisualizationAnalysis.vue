<template>
  <EChartsWrapper :pieChartOption="pieChartOptionData" :barChartOption="barChartOptionData"
    :tableOption="tableOptionData" />
</template>

<script>
import axios from "axios";
import EChartsWrapper from '@/components/EChartsWrapper.vue'

const baseURL = process.env.VUE_APP_API_FileServer_URL;

export default {
  components: {
    EChartsWrapper
  },
  data() {
    return {
      pieChartOptionData: {  //饼状图数据
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        toolbox: {
          feature: {
            saveAsImage: {},
            dataView: { readOnly: false },
            //restore: {},
            //magicType: { type: ['line', 'bar'] }
          }
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['0%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '40',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [],
          }
        ]
      },

      barChartOptionData: {  //柱状图数据
        // title: {
        //   text: 'Iteration Chart'
        // },
        tooltip: {},
        toolbox: {
          feature: {
            saveAsImage: {},
            dataView: { readOnly: false },
            restore: {},
            magicType: { type: ['line', 'bar'] }
          }
        },
        xAxis: {
          data: []
        },
        yAxis: {},
        series: [{
          name: 'Iterations',
          type: 'bar',
          data: []
        }]
      },

      tableOptionData: {  //底部表格数据
        columns: [],
        data: []
      }
    }
  },

  created() {
    this.fetchPieChartData()
    this.fetchBarChartData()
    this.fetchTableData()
  },
  methods: {
    fetchPieChartData() {
      axios.get(`${baseURL}/`)  // 根据实际后端 API 调整 URL
        .then(response => {
          let data = response.data;
          console.log(data);
          let passedCount = 0;
          let notPassedCount = 0;

          data = [{ correctness: true }, { correctness: true }, { correctness: false }]
          // 假设后端返回格式是 [{correctness: true,...},{correctness: true,...},...]
          // 这样格式的测试结果的字典列表
          // 统计 Passed 和 Not Passed 的数量
          data.forEach(item => {
            if (item.correctness) {
              passedCount++;
            } else {
              notPassedCount++;
            }
          });

          // 更新 pieChartOptionData
          this.pieChartOptionData.series[0].data = [
            { value: passedCount, name: '通过' },
            { value: notPassedCount, name: '未通过' }
          ];
        })
        .catch(error => {
          console.error('Failed to fetch pie chart data:', error);
        });
    },
    fetchBarChartData() {
      axios.get(`${baseURL}/`)  // 根据实际后端 API 调整 URL
        .then(response => {
          console.log(response.data);
          this.barChartOptionData.xAxis.data = ['1.0版本', '2.0版本', '3.0版本'];
          this.barChartOptionData.series[0].data = [1, 2, 10];
        })
    },
    fetchTableData() {
      axios.get(`${baseURL}/`)  // 根据实际后端 API 调整 URL
        .then(response => {
          console.log(response.data);
          this.tableOptionData.data = [
            { version: 'v1.0', dataset: '强健壮等价类', result: '1/10', defectDescription: 'noob' },
            { version: 'v2.0', dataset: '强健壮等价类', result: '2/10', defectDescription: 'noob' },
            { version: 'v3.0', dataset: '强健壮等价类', result: '10/10', defectDescription: 'brilliant' }
          ];
          this.tableOptionData.columns = [
            { title: '版本', key: 'version' },
            { title: '测试数据集', key: 'dataset' },
            { title: '测试结果', key: 'result' },
            { title: '缺陷描述', key: 'defectDescription' }
          ];
        })
    }
  }
}
</script>
