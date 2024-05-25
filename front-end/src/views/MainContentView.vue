<template>
  <router-view />
</template>

<script setup>
import { computed, watch } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const store = useStore(); // 使用 Vuex 的 store
const router = useRouter(); // 使用 Vue Router

// 获取侧边栏选择的类型
const sidebarType = computed(() => store.state.sidebarType);
// 获取侧边栏选择
const sidebarSelection = computed(() => store.state.sidebarSelection);
// 获取项目信息
const project = computed(() => store.state.project);

// 动态导航函数
const navigateTo = (type, selection) => {
  if (type === "练习") {
    if (selection === "项目设置") {
      router.push("/project-set");
    } else if (selection === "测试") {
      router.push(project.value.name !== "" ? `/${project.value.name}/ordinary-test` : "/ordinary-test");
    }
    else{
      router.push({name:"NotFound"});
    }
  } else if (type === "项目") {
    switch (selection) {
      case "项目介绍":
        router.push("/project-info");
        break;
      case "单元测试":
        router.push("/unit-test");
        break;
      case "集成测试":
        router.push("/integration-test");
        break;
      case "系统测试":
        router.push("/system-test");
        break;
      case "性能测试":
        router.push("/property-test");
        break;
      default:
        router.push({name:"NotFound"});
        break;
    }
  } else {
    router.push("/");
  }
};

// 监听侧边栏选择变化
watch([sidebarType, sidebarSelection], ([newType, newSelection]) => {
  navigateTo(newType, newSelection);
});
</script>
