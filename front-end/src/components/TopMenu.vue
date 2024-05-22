<template>
  <el-menu
    :default-active="activeIndex"
    class="el-menu-demo nav-bar"
    mode="horizontal"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
  >
    <el-menu-item v-if="sidebarType == '练习'">
      <template #title><strong>练习：{{ projectName }}</strong></template>
    </el-menu-item>
    <el-menu-item v-else-if="sidebarType == '项目'">
      <template #title><strong>项目：{{ sidebarSelection }}</strong></template>
    </el-menu-item>
    <el-menu-item v-else>
      <template #title><strong>欢迎使用SoftwareTestPlatform</strong></template>
    </el-menu-item>
    <!-- 循环渲染菜单项 -->
    <el-menu-item v-show="sidebarType == '练习'"
      v-for="menuItem in menuItems"
      :key="menuItem.index"
      :index="menuItem.index"
    >
      {{ menuItem.label }}
    </el-menu-item>
    <el-sub-menu style="position: absolute; top: 50%; right: 0">
      <template #title>
        <el-icon><QuestionFilled /></el-icon>
      </template>
      <el-menu-item @click="navigateTo('About')">关于本站</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import { useStore } from "vuex";
import { QuestionFilled } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
 
const store = useStore(); // 使用 Vuex 的 store

// 获取侧边栏选择的类型
const sidebarType = computed(() => store.state.sidebarType);
// 获取侧边栏选择
const sidebarSelection = computed(() => store.state.sidebarSelection);
// 获取项目名称
const projectName = computed(() => store.state.project.name);

const activeIndex = ref("1");

// 导航栏菜单项数组
const menuItems = ref([]);

const router = useRouter(); // 使用 Vue Router

const navigateTo = (routeName) => {
  store.commit('updateSidebarSelection',{});
  router.push({ name: routeName });
};

// 监视侧边栏选择的变化
watch(sidebarSelection, (newVal) => {
  switch (newVal) {
    case "项目设置":
      menuItems.value = [
        { index: "1", label: "项目信息" },
        { index: "2", label: "工作台" },
        { index: "3", label: "项目进度" },
      ];
      break;
    case "测试":
      menuItems.value = [
        { index: "1", label: "问题描述" },
        { index: "2", label: "结果查询" },
        { index: "3", label: "可视化分析" },
      ];
      break;
    default:
      break;
  }
});

// 初始化菜单项
switch (sidebarSelection.value) {
  case "练习":
    menuItems.value = [
      { index: "1", label: "项目信息" },
      { index: "2", label: "工作台" },
      { index: "3", label: "项目进度" },
    ];
    break;
  case "项目设置":
    menuItems.value = [
      { index: "1", label: "项目信息" },
      { index: "2", label: "工作台" },
      { index: "3", label: "项目进度" },
    ];
    break;
  case "单元测试":
    menuItems.value = [
      { index: "1", label: "接口文档" },
      { index: "2", label: "接口测试" },
      { index: "3", label: "加载测试" },
      { index: "4", label: "安全测试" },
    ];
    break;
  default:
    break;
}
</script>

<style scoped>
.nav-bar {
  height: 5vh;
}
</style>
