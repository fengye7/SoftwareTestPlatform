<template>
  <el-card>
    <template #header>
      <h3>Junit模块</h3>
    </template>

    <!-- 在线Junit代码编辑器 -->
    <el-form :model="form" inline="inline">
      <el-form-item label=""></el-form-item>
    </el-form>
    <el-form-item label="Junit代码">
      <el-input
        type="textarea"
        :rows="10"
        placeholder="在此输入Junit代码"
        v-model="junitCode"
      ></el-input>
    </el-form-item>

    <!-- 上传Junit文件 -->
    <el-upload
      class="upload-junit"
      action="/upload-junit"
      :show-file-list="false"
      :on-success="handleUploadSuccess"
    >
      <el-button type="primary">上传Junit文件</el-button>
    </el-upload>
  </el-card>
</template>

<script setup>
import { ElUpload, ElButton, ElForm, ElFormItem, ElInput, ElCard } from 'element-plus';
</script>
