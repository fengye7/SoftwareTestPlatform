<template>
  <div class="container">
    <!-- 左边的卡片 -->
    <el-card class="left-card">
      <h2>当前选择的项目信息</h2>
      <!-- <div v-if="selectedProject.name != ''"> -->
      <p>项目名称：{{ selectedProject.name }}</p>
      <p>项目管理者：{{ selectedProject.manager }}</p>
      <p>项目描述：{{ selectedProject.description }}</p>
      <p>项目创建时间：{{ selectedProject.date }}</p>
      <p v-show="selectedProject.resource !== ''">
        项目源地址：{{ selectedProject.resource }}
      </p>
      <!-- </div> -->
      <!-- <div v-else>
        <p>暂未选择项目</p>
      </div> -->

      <!--这里添加展示更多来自后端的信息-->
    </el-card>

    <!-- 右边的卡片 -->
    <el-card class="right-card">
      <h2>项目列表</h2>
      <el-button type="primary" @click="clickCreateProject">创建项目</el-button>
      <el-button type="primary" @click="clickModifyProject">修改项目</el-button>
      <el-button type="primary" @click="clickDeleteProject">删除项目</el-button>
      <hr />
      <el-pagination v-model="currentPage" :page-size="pageSize" :total="totalProjects" layout="prev, pager, next"
        @current-change="handleCurrentChange"></el-pagination>
      <el-divider></el-divider>
      <div class="scroll-container">
        <el-card class="project-card fengye7Font" v-for="project in displayedProjects" :key="project.id"
          @click="selectProject(project)">
          <p>项目名称：{{ project.name }}</p>
          <p>项目管理者：{{ project.manager }}</p>
          <p>项目描述：{{ project.description }}</p>
          <p>项目创建时间：{{ project.date }}</p>
          <p v-show="project.resource != ''">
            项目源地址：{{ project.resource }}
          </p>
        </el-card>
      </div>
    </el-card>

    <!-- 创建项目对话框 -->
    <el-dialog v-model="createDialogVisible" title="创建项目">
      <el-form :model="projectInfo" label-width="auto" style="max-width: 600px">
        <el-form-item label="项目名">
          <el-input v-model="projectInfo.name" placeholder="请输入项目名" />
        </el-form-item>
        <el-form-item label="管理者">
          <el-input v-model="projectInfo.manager" placeholder="请输入项目管理者" />
        </el-form-item>
        <el-form-item label="日期">
          <el-date-picker v-model="projectInfo.date" type="date" placeholder="Pick a date" style="width: 100%" />
        </el-form-item>
        <el-form-item label="项目源">
          <el-input v-model="projectInfo.resource" placeholder="输入项目源地址(可选)" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="projectInfo.description" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="createProject">创建</el-button>
          <el-button @click="createDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 修改项目对话框 -->
    <el-dialog v-model="modifyDialogVisible" title="修改项目">
      <div v-if=isProjectSeted>
        <el-form :model="projectInfo" label-width="auto" style="max-width: 600px">
          <el-form-item label="项目名">
            <el-input v-model="projectInfo.name" placeholder="请输入项目名" />
          </el-form-item>
          <el-form-item label="管理者">
            <el-input v-model="projectInfo.manager" placeholder="请输入项目管理者" />
          </el-form-item>
          <el-form-item label="日期">
            <el-date-picker type="date" v-model="projectInfo.date" placeholder="Pick a date" style="width: 100%" />
          </el-form-item>
          <el-form-item label="项目源">
            <el-input v-model="projectInfo.resource" placeholder="输入项目源地址(可选)" />
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="projectInfo.description" type="textarea" />
          </el-form-item>
        </el-form>
        <el-form-item>
          <el-button type="primary" @click="modifyProject">修改</el-button>
          <el-button @click="modifyDialogVisible = false">取消</el-button>
        </el-form-item>
      </div>
      <div v-else>
        <p>暂未选择项目！</p>
      </div>
    </el-dialog>

    <!-- 删除项目对话框 -->
    <el-dialog v-model="deleteDialogVisible" title="删除项目">
      <div v-if=isProjectSeted>
        <el-form label-width="auto" style="max-width: 600px">
          <p>您确定要删除项目{{ selectedProject.name }}吗</p>
          <el-form-item>
            <el-button type="primary" @click="deleteProject">删除</el-button>
            <el-button @click="deleteDialogVisible = false">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div v-else>
        <p>暂未选择项目！</p>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { ElCard, ElDivider, ElButton, ElDialog } from "element-plus";
import axios from "axios";

const store = useStore();
const selectedProject = computed(() => store.state.project);
const baseURL = process.env.VUE_APP_API_DatabaseServer_URL;

const createDialogVisible = ref(false); // 控制项目创建对话框显示与隐藏
const modifyDialogVisible = ref(false);
const deleteDialogVisible = ref(false);
let isProjectSeted = (selectedProject.value.manager == '');
const projectInfo = reactive({
  name: "",
  description: "",
  date: "",
  manager: "",
  resource: "", // 项目若开源，可选url
});
const projects = ref([]);
/**项目列表控制 **/
const currentPage = ref(1);
const pageSize = 10;
const totalProjects = computed(() => projects.value.length); // 计算总项目数
const displayedProjects = computed(() => {
  // 计算当前页面显示的项目列表
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  return projects.value.slice(start, end);
});
const handleCurrentChange = (page) => {
  // 处理分页变化
  currentPage.value = page;
};
const selectProject = (project) => {
  // 选择项目
  // 在此处处理选中项目的逻辑，例如向 Vuex 存储选中的项目信息
  // console.log("选中的项目：", project);
  store.commit("setProject", project);
  isProjectSeted = true;
};

// 获取项目列表
const getProjects = async () => {
  try {
    const response = await axios.get(`${baseURL}/projects`); // 根据实际的后端API路径进行调整
    projects.value = response.data; // 将获取的项目数据赋值给 projects 数组
  } catch (error) {
    console.error("Failed to fetch projects:", error);
  }
};

// 在组件创建时调用获取项目列表的函数
onMounted(() => {
  getProjects();
});

//点击创建项目按钮
const clickCreateProject = async () => {
  clearProjectInfoVar();
  createDialogVisible.value = true;
};

//点击修改项目按钮
const clickModifyProject = async () => {
  setProjectInfoVar();
  modifyDialogVisible.value = true;
};

//点击删除项目按钮
const clickDeleteProject = async () => {
  deleteDialogVisible.value = true;
};

// 创建项目
const createProject = async () => {
  try {
    // 检查除 resource 外的所有属性是否为空
    const { name, description, date, manager, resource } = projectInfo;
    if (!name || !description || !date || !manager) {
      alert("请完整填写项目信息");
      return; // 如果有任何一个属性为空，则提醒用户并返回，不发送请求
    }

    // 格式化日期为 ISO 8601 格式
    const isoDate = new Date(date).toISOString().split('T')[0];

    const params = new URLSearchParams();
    params.append("name", name);
    params.append("description", description);
    params.append("date", isoDate);
    params.append("manager", manager);
    params.append("resource", resource);

    const response = await axios.post(`${baseURL}/projects`, params); // 使用 axios.post 发送 POST 请求，将 projectInfo 数据发送到后端API
    console.log("Project created successfully:", response.data);
    // 创建项目成功后，您可能需要刷新项目列表以显示新项目
    getProjects(); // 重新获取项目列表
    clearProjectInfoVar(); //重置projectInfo变量
    createDialogVisible.value = false; // 关闭创建项目对话框
  } catch (error) {
    console.error("Failed to create project:", error);
    alert("创建项目失败，API请求失败!!!");
  }
};

const modifyProject = async () => {
  try {
    // 检查除 resource 外的所有属性是否为空
    const { name, description, date, manager, resource } = projectInfo;
    if (!name || !description || !date || !manager) {
      alert("请完整填写项目信息");
      return; // 如果有任何一个属性为空，则提醒用户并返回，不发送请求
    }

    // 格式化日期为 ISO 8601 格式
    const isoDate = new Date(date).toISOString().split('T')[0];

    const params = new URLSearchParams();
    params.append("oldName", selectedProject.value.name);
    params.append("newName", name);
    params.append("description", description);
    params.append("date", isoDate);
    params.append("manager", manager);
    params.append("resource", resource);

    const response = await axios.post(`${baseURL}/projects/modify`, params); // 使用 axios.post 发送 POST 请求，将 projectInfo 数据发送到后端API
    console.log("Project modifyed successfully:", response.data);
    // 创建项目成功后，您可能需要刷新项目列表以显示新项目
    getProjects(); // 重新获取项目列表
    clearProjectInfoVar(); //重置projectInfo变量
    modifyDialogVisible.value = false; // 关闭创建项目对话框
  } catch (error) {
    console.error("Failed to create project:", error);
    alert("创建项目失败，API请求失败!!!");
  }
};

const deleteProject = async () => {
  try {
    const name = selectedProject.value.name;
    const params = new URLSearchParams();
    params.append("name", name);

    const response = await axios.delete(`${baseURL}/projects/delete?${params.toString()}`);
    console.log(`Project ${name} deleted successfully:`, response.data);
    // 创建项目成功后，您可能需要刷新项目列表以显示新项目
    getProjects(); // 重新获取项目列表
    deleteDialogVisible.value = false; // 关闭创建项目对话框
  } catch (error) {
    console.error("Failed to create project:", error);
    alert("创建项目失败，API请求失败!!!");
  }
};

function clearProjectInfoVar() {
  projectInfo.name = "";
  projectInfo.description = "";
  projectInfo.date = "";
  projectInfo.manager = "";
  projectInfo.resource = "";
}

//将projectInfo变量设为selectedProject
function setProjectInfoVar() {
  projectInfo.name = selectedProject.value.name;
  projectInfo.description = selectedProject.value.description;
  projectInfo.date = selectedProject.value.date;
  projectInfo.manager = selectedProject.value.manager;
  projectInfo.resource = selectedProject.value.resource;
}

</script>


<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  height: 90vh;
  margin: 20px;
}

.left-card {
  text-align: left;
  align-content: center;
  padding: 20px;
  width: 40%;
  margin-right: 5px;
  font-family: 'Courier New', Courier, monospace;
  color: rgb(43, 43, 123);
  font-size: 1.5em;
}

.right-card {
  width: 60%;
  margin-left: 5px;
}

.scroll-container {
  max-height: 60vh;
  /* 设置最大高度，超出时显示滚动条 */
  overflow-y: auto;
  /* 垂直滚动条 */
}

.project-card {
  cursor: pointer;
  transition: background-color 0.3s;
  /* 添加过渡效果 */
  border-radius: 10px;
  margin: 10px;
  padding: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.project-card:hover {
  background-color: #3ed0ea;
  /* 悬停时的背景颜色 */
}

.project-card:active {
  transform: translateY(2px);
  /* 点击时的垂直位移效果 */
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  /* 点击时的阴影效果 */
}
</style>
