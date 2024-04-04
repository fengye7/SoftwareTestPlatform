import { createStore } from 'vuex'

export default createStore({
  state: {
    projectName: '三角形？', // project name
    sidebarSelection: '项目设置', // 侧边栏选择
  },
  getters: {
  },
  mutations: {
    updateSidebarSelection(state, selection) { // 侧边栏选择更新
      state.sidebarSelection = selection;
    }
  },
  actions: {
  },
  modules: {
  },
})
