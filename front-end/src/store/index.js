import { createStore } from 'vuex';

export default createStore({
  state: {
    project: {
      name: '未设置项目',
      description: '',
      date: '',
      manager: '',
      resource: ''
    }, // 默认项目为空
    sidebarType: '', // 属于练习还是项目
    sidebarSelection: '', // 侧边栏选择
  },
  getters: {},
  mutations: {
    updateSidebarSelection(state, payload) { // 侧边栏选择更新
      state.sidebarType = payload.type;
      state.sidebarSelection = payload.selection;
    },
    setProject(state, project) {
      state.project = project;
    },    
  },
  actions: {},
  modules: {},
});
