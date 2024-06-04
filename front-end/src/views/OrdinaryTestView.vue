<template>
  <div class="container">
    <div class="left-panel">
      <!-- 左侧面板的内容 -->
      <component :is="currentComponent"></component>
    </div>
    <div class="right-panel">
      <ExerciseControlPanel />
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import InformationPanel from '../components/InformationPanel.vue';
import TestResult from '../components/TestResult.vue';
import VisualizationAnalysis from '../components/VisualizationAnalysis.vue';
import ExerciseControlPanel from '../components/ExerciseControlPanel.vue';

let index = '3';
const currentComponent = computed(() => {
  switch (index) {
    case '1':
      return InformationPanel;
    case '2':
      return TestResult;
    case '3':
      return VisualizationAnalysis;
    default:
      return InformationPanel;
  }
});
</script>

<style scoped>
.container {
  display: flex;
  height: 95vh;
}

.left-panel {
  flex: 6;
  padding-right: 20px;
  border-right: 5px solid #9babcf;
  /* 左右分割线 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.right-panel {
  flex: 4;
  padding-left: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #eff5ff;
}
</style>
