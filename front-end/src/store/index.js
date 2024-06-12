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
    ordinaryTestOptIndex: 1, // 选择的练习测试的内容tab
    exerciseTest : {
      scriptName:'',
      testSetName:'',
    }
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
    setExerciseTestScript(state, name) {
      state.exerciseTest.scriptName = name;
    },
    setExerciseTestSet(state, name) {
      state.exerciseTest.testSetName = name;
    },
    setExerciseTest(state, test) {
      state.exerciseTest = test;
    },
    updateOrdinaryTestOptIndex(state,index){
      state.ordinaryTestOptIndex = index;
    }    
  },
  actions: {},
  modules: {},
});
