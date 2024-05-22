<template>
  <div class="container">
     <div class="left-panel">
     </div>
     <div class="right-panel">
      <exercise-control-panel/>
     </div>
  </div>
</template>

<script setup>
import ExerciseControlPanel from '../components/ExerciseControlPanel.vue'

</script>

<style scoped>
.container {
  display: flex;
}

.left-panel {
  flex: 1;
  padding-right: 20px;
  border-right: 1px solid #ebeef5; /* 左右分割线 */
  height: 90vh;
}

.right-panel {
  flex: 1;
  padding-left: 20px;
  height: 90vh;
}
</style>
