<template>
  <div class="container">
    <div class="left-panel">
      <!-- Excel编辑/显示/下载/上传功能模块 -->
      <excel-module
        v-model:excelData="excelData"
        @downloadExcel="downloadExcel"
        @uploadExcel="uploadExcel"
      ></excel-module>
    </div>
    <div class="right-panel">
      <!-- 在线Junit代码编辑器或直接上传源文件的入口 -->
      <JUnitModule/>
    </div>
  </div>
</template>

<script setup>
import ExcelModule from '@/components/ExcelModule.vue';
import JUnitModule from '@/components/JUnitModule.vue';
</script>

<style scoped>
.container {
  display: flex;
}

.left-panel {
  flex: 1;
  padding-right: 20px;
  border-right: 1px solid #ebeef5; /* 左右分割线 */
}

.right-panel {
  flex: 1;
  padding-left: 20px;
}
</style>
