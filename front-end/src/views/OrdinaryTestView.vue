<template>
  <div class="container">
     <div class="left-panel">
       <!-- 左侧面板的内容 -->
     </div>
     <div class="right-panel">
       <ExerciseControlPanel/>
     </div>
  </div>
</template>

<script setup>
import ExerciseControlPanel from '../components/ExerciseControlPanel.vue';
</script>

<style scoped>
.container {
  display: flex;
  height: 95vh;
}

.left-panel {
  flex: 6;
  padding-right: 20px;
  border-right: 5px solid #9babcf; /* 左右分割线 */
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
