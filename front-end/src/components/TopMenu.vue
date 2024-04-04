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
      <template #title>项目：{{projectName}}</template>
      <el-menu-item index="2-1">item one</el-menu-item>
      <el-menu-item index="2-2">item two</el-menu-item>
      <el-menu-item index="2-3">item three</el-menu-item>
      <el-sub-menu index="2-4">
        <template #title>item four</template>
        <el-menu-item index="2-4-1">item one</el-menu-item>
        <el-menu-item index="2-4-2">item two</el-menu-item>
        <el-menu-item index="2-4-3">item three</el-menu-item>
      </el-sub-menu>
    </el-sub-menu>
    <!-- 循环渲染菜单项 -->
    <el-menu-item v-for="menuItem in menuItems" :key="menuItem.index" :index="menuItem.index">
      {{ menuItem.label }}
    </el-menu-item>
  </el-menu>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { useStore } from 'vuex'

const store = useStore() // 使用 Vuex 的 store

// 获取侧边栏选择
const sidebarSelection = computed(() => store.state.sidebarSelection)
// 获取项目名称
const projectName = computed(() => store.state.projectName)

const activeIndex = ref('1')

// 导航栏菜单项数组
const menuItems = ref([]) // 初始化为空数组

// 监听侧边栏选择变化
watch(sidebarSelection, (newVal) => {
  // 根据侧边栏选择更新菜单项
  switch (newVal) {
    case '项目设置':
      menuItems.value = [
        { index: '1', label: '项目信息' },
        { index: '2', label: 'Workspace' },
        { index: '3', label: 'Info', disabled: true },
        { index: '4', label: 'Orders' }
      ]
      break
    case '单元测试':
      menuItems.value = [
        { index: '1', label: 'API Documentation' },
        { index: '2', label: 'Interface Testing' },
        { index: '3', label: 'Load Testing' },
        { index: '4', label: 'Security Testing' }
      ]
      break
    // 其他情况
  }
})

// 初始化时根据侧边栏选择设置菜单项
switch (sidebarSelection.value) {
  case '项目设置':
    menuItems.value = [
      { index: '1', label: '项目信息' },
      { index: '2', label: 'Workspace' },
      { index: '3', label: 'Info', disabled: true },
      { index: '4', label: 'Orders' }
    ]
    break
  case '接口测试':
    menuItems.value = [
      { index: '1', label: 'API Documentation' },
      { index: '2', label: 'Interface Testing' },
      { index: '3', label: 'Load Testing' },
      { index: '4', label: 'Security Testing' }
    ]
    break
  // 其他情况
}
</script>

<style scoped>

</style>
