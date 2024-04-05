<template>
  <router-view />
</template>

<script setup>
import { computed, watch } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const store = useStore(); // 使用 Vuex 的 store
const router = useRouter(); // 使用 Vue Router

// 获取侧边栏选择
const sidebarSelection = computed(() => store.state.sidebarSelection);
// 获取项目信息
const project = computed(() => store.state.project);

// 监听侧边栏选择变化
watch(sidebarSelection, (newVal) => {
  // 根据侧边栏选择动态导航到不同的路由
  switch (newVal) {
    case "项目设置":
      router.push("/project-set");
      break;
    case "一般测试":
      router.push(project.value.name!='未设置项目' ? `/${project.value.name}/ordinary-test` : "/ordinary-test");
      break;
    case "单元测试":
      router.push(project.value.name!='未设置项目' ? `/${project.value.name}/unit-test` : "/unit-test");
      break;
    default:
      break;
  }
});
</script>
