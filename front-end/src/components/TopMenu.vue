<template>
  <el-menu
    :default-active="activeIndex"
    class="el-menu-demo"
    mode="horizontal"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
  >
    <el-sub-menu index="1">
      <template #title>项目：{{ projectName }}</template>
      <el-menu-item index="1-1">重置项目设置</el-menu-item>
    </el-sub-menu>
    <!-- 循环渲染菜单项 -->
    <el-menu-item
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
      <el-menu-item>新手教程</el-menu-item>
      <el-menu-item>关于本站</el-menu-item>
      <el-menu-item>关于我们</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import { useStore } from "vuex";
import { QuestionFilled } from "@element-plus/icons-vue";

const store = useStore(); // 使用 Vuex 的 store

// 获取侧边栏选择
const sidebarSelection = computed(() => store.state.sidebarSelection);
// 获取项目名称
const projectName = computed(() => store.state.project.name);

const activeIndex = ref("1");

// 导航栏菜单项数组
const menuItems = ref([]);

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
});

// 初始化菜单项
switch (sidebarSelection.value) {
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
/* 样式 */
</style>
