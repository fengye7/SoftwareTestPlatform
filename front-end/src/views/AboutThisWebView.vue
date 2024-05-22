<template>
  <el-container class="about-page">
    <el-header class="header">
      <h1>关于本站</h1>
    </el-header>
    <el-main class="content">
      <el-card>
        <h2>Web应用基本信息</h2>
        <el-divider></el-divider>
        <p>
          本站是一个<em>demo产品</em>，旨在展示和演示软件测试相关的知识和技术。
        </p>
        <p>
          本站由<em>同济大学软件学院</em>开发，旨在帮助学生更好地理解和应用软件测试技术。
        </p>
        <p>指导教师: <strong>杜庆峰</strong>教授。</p>
        <p>
          本产品的需求源自于《<strong>软件测试</strong>》课程的要求，旨在为学生提供一个实际操作的平台。
        </p>
        <el-container>
          <el-card>
            <h4>开发环境</h4>
            <el-divider></el-divider>
            <p>开发工具: <strong>Visual Studio Code</strong></p>
            <p>开发语言: <strong>JavaScript</strong></p>
            <p>开发框架: <strong>Vue 3</strong></p>
            <p>前端框架: <strong>Element Plus</strong></p>
            <p>后端框架: <strong>Spring Boot</strong></p>
          </el-card>
          <el-card>
            <h4>团队成员</h4>
            <el-divider></el-divider>
            <p>
              <strong>2152814周成杰</strong><br>
              <strong>2151415张逸旸</strong><br>
              <strong>2153699周禹丞</strong><br>
              <strong>2056173何红昌</strong>
            </p>
          </el-card>
        </el-container>
      </el-card>
    </el-main>
  </el-container>
</template>

<script setup>
import { ElContainer, ElHeader, ElMain, ElCard, ElDivider } from "element-plus";
</script>

<style scoped>
.about-page {
  background-color: #f5f5f5;
  padding: 20px;
}

.header {
  background-color: #409eff;
  color: #fff;
  text-align: center;
  line-height: center;
  padding: 10px 0;
}

.content {
  display: flex;
  justify-content: center;
}

.el-card {
  width: 80%;
  max-width: 600px;
  margin-top: 20px;
}

.el-card h2 {
  font-size: 24px;
  margin-bottom: 10px;
}

.el-card p {
  font-size: 16px;
  line-height: 1.5;
  margin: 10px 0;
}
</style>
