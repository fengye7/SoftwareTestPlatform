import { createStore } from 'vuex'

export default createStore({
  state: {
    project: {
      name: '未设置项目',
      description: '',
      date: '',
      manager: '',
      resource: ''
    }, // 默认项目为空
    sidebarSelection: '项目设置', // 侧边栏选择
  },
  getters: {
  },
  mutations: {
    updateSidebarSelection(state, selection) { // 侧边栏选择更新
      state.sidebarSelection = selection;
    },
    setProject(state, project) {
      state.project = project;
    },    
  },
  actions: {
  },
  modules: {
  },
})
