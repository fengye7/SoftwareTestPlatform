<template>
  <el-aside
    class="nav-container"
    @mouseenter="showNav = true"
    @mouseleave="showNav = false"
  >
    <el-container style="height: 100%;">
      <el-header @click="navigateTo('Home')">
        <el-icon class="nav-icon"><Menu /></el-icon>
        <el-text v-show="showNav || fixedNav"><strong>TestPlatform</strong></el-text>
      </el-header>
      <el-container style="height: 100%;">
        <el-menu
          default-active="1-1"
          class="el-menu-vertical-demo"
        >
          <el-sub-menu index="1">
            <template #title>
              <el-icon class="nav-icon"><Document /></el-icon>
              <el-text v-show="showNav || fixedNav">练习</el-text>
            </template>
            <el-menu-item index="1-1" @click="updateSidebarSelection('练习','项目设置')">
              <el-icon class="nav-icon"><Guide /></el-icon>
              <el-text v-show="showNav || fixedNav">项目设置</el-text>
            </el-menu-item>
            <el-menu-item index="1-2" @click="updateSidebarSelection('练习','测试')">
              <el-icon class="nav-icon"><Operation /></el-icon>
              <el-text v-show="showNav || fixedNav">测试</el-text>
            </el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon class="nav-icon"><Platform /></el-icon>
              <el-text v-show="showNav || fixedNav">项目测试</el-text>
            </template>
            <el-menu-item index="2-1" @click="updateSidebarSelection('项目','项目介绍')">
              <el-icon class="nav-icon"><FullScreen /></el-icon>
              <el-text v-show="showNav || fixedNav">项目介绍</el-text>
            </el-menu-item>
            <el-menu-item index="2-2" @click="updateSidebarSelection('项目','单元测试')">
              <el-icon class="nav-icon"><Aim /></el-icon>
              <el-text v-show="showNav || fixedNav">单元测试</el-text>
            </el-menu-item>
            <el-menu-item index="2-3" @click="updateSidebarSelection('项目','集成测试')">
              <el-icon class="nav-icon"><Connection /></el-icon>
              <el-text v-show="showNav || fixedNav">集成测试</el-text>
            </el-menu-item>
            <el-menu-item index="2-4" @click="updateSidebarSelection('项目','系统测试')">
              <el-icon class="nav-icon"><Refresh /></el-icon>
              <el-text v-show="showNav || fixedNav">系统测试</el-text>
            </el-menu-item>
            <!-- <el-menu-item index="2-5" @click="updateSidebarSelection('项目','性能测试')">
              <el-icon class="nav-icon"><TrendCharts /></el-icon>
              <el-text v-show="showNav || fixedNav">性能测试</el-text>
            </el-menu-item> -->
          </el-sub-menu>
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
import {useRouter} from "vue-router"
import {
  Menu,
  Guide,
  FullScreen,
  Connection,
  Refresh,
  Operation,
  Document,
  Aim,
  Platform,
} from "@element-plus/icons-vue";

const showNav = ref(false);
const fixedNav = ref(false);

const store = useStore(); // 使用 Vuex 的 store

// 更新侧边栏选择的方法
const updateSidebarSelection = (type,selection) => {
  store.commit('updateSidebarSelection',{ type, selection});
}

const router = useRouter(); // 使用 Vue Router

const navigateTo = (routeName) => {
  store.commit('updateSidebarSelection',{});
  router.push({ name: routeName });
};
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
  color: #edff9c;
}

.el-menu-vertical-demo {
  width: 100%;
  background-color: #043d72;
}

.el-menu-item {
  background-color: #043d72;
  display: flex;
  align-items: center;
}

.el-sub-menu .el-text {
  margin-right: 10px;
  color: rgba(255, 102, 7, 0.5);
}
</style>
