<template>
  <div class="container">
    <div class="left-panel">
      <component :is="currentComponent.component" v-bind="currentComponent.props" />
    </div>
    <div class="right-panel">
      <ExerciseControlPanel @getTestResults="updateTestResults" />
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue';
import { useStore } from 'vuex';
import InformationPanel from '../components/InformationPanel.vue';
import TestResult from '../components/TestResult.vue';
import VisualizationAnalysis from '../components/VisualizationAnalysis.vue';
import ExerciseControlPanel from '../components/ExerciseControlPanel.vue';

const testResults = ref([]);

const updateTestResults = (results) => {
  testResults.value = results;
  console.log("父组件：", testResults.value);
}

const store = useStore();
const index = computed(() => store.state.ordinaryTestOptIndex);
const currentComponent = computed(() => {
  switch (index.value) {
    case '1':
      return { component: InformationPanel, props: {} };
    case '2':
      return { component: TestResult, props: { responseData: testResults.value } };
    case '3':
      return { component: VisualizationAnalysis, props: { responseData: testResults.value } };
    default:
      return { component: InformationPanel, props: {} };
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
