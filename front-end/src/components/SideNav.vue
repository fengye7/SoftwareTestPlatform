<template>
  <el-aside
    class="nav-container"
    @mouseenter="showNav = true"
    @mouseleave="showNav = false"
  >
    <el-container style="height: 100%;">
      <el-header>
        <el-icon class="nav-icon"><Menu /></el-icon>
        <el-text v-show="showNav || fixedNav">TestPlatform</el-text>
      </el-header>
      <el-container style="height: 100%;">
        <el-menu default-active="1" class="el-menu-vertical-demo">
          <el-menu-item index="1" @click="updateSidebarSelection('项目设置')">
            <el-icon class="nav-icon"><Guide /></el-icon>
            <el-text v-show="showNav || fixedNav">项目设置</el-text>
          </el-menu-item>
          <el-menu-item index="2" @click="updateSidebarSelection('一般测试')">
            <el-icon class="nav-icon"><Document /></el-icon>
            <el-text v-show="showNav || fixedNav">一般测试</el-text>
          </el-menu-item>
          <el-menu-item index="3" @click="updateSidebarSelection('单元测试')">
            <el-icon class="nav-icon"><Aim /></el-icon>
            <el-text v-show="showNav || fixedNav">单元测试</el-text>
          </el-menu-item>
          <el-menu-item index="4" @click="updateSidebarSelection('系统测试')">
            <el-icon class="nav-icon"><Platform /></el-icon>
            <el-text v-show="showNav || fixedNav">系统测试</el-text>
          </el-menu-item>
          <el-menu-item index="5" @click="updateSidebarSelection('性能测试')">
            <el-icon class="nav-icon"><TrendCharts /></el-icon>
            <el-text v-show="showNav || fixedNav">性能测试</el-text>
          </el-menu-item>
        </el-menu>
      </el-container>
      <el-footer v-show="showNav" style="text-align: right;">
        <el-checkbox v-model="fixedNav">Pin</el-checkbox>
      </el-footer>
    </el-container>
  </el-aside>
</template>

<script setup>
import { ref } from "vue";
import { useStore } from "vuex";
import {
  Menu,
  Guide,
  Document,
  Aim,
  Platform,
  TrendCharts,
} from "@element-plus/icons-vue";

const showNav = ref(false);
const fixedNav = ref(false);

const store = useStore(); // 使用 Vuex 的 store

// 更新侧边栏选择的方法
const updateSidebarSelection = (selection) => {
  store.commit('updateSidebarSelection', selection);
}
</script>

<style scoped>
.nav-container {
  background-color: #043d72;
  height: 100vh;
  width: auto;
  margin: 0%;
}

.nav-icon {
  cursor: pointer;
  text-align: center;
  height: 50px;
  width: auto;
  line-height: 50px;
  color: #e8f5ab;
}

.el-menu-vertical-demo {
  width: 100%;
  background-color: #043d72;
}

.el-menu-item {
  display: flex;
  align-items: center;
}

.el-menu-item el-icon,
.el-menu-item el-text {
  margin-right: 10px;
}
</style>
