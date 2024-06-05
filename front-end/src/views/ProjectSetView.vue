<template>
  <div class="container">
    <!-- 左边的卡片 -->
    <el-card class="left-card">
      <h2>当前选择的项目信息</h2>
      <div v-if="selectedProject.name != ''">
        <p>项目名称：{{ selectedProject.name }}</p>
        <p>项目管理者：{{ selectedProject.manager }}</p>
        <p>项目描述：{{ selectedProject.description }}</p>
        <p>项目创建时间：{{ selectedProject.date }}</p>
        <p v-show="selectedProject.resource !== ''">
          项目源地址：{{ selectedProject.resource }}
        </p>
      </div>
      <div v-else>
        <p>暂未选择项目</p>
      </div>

      <!--这里添加展示更多来自后端的信息-->
    </el-card>

    <!-- 右边的卡片 -->
    <el-card class="right-card">
      <h2>项目列表</h2>
      <el-button type="primary" @click="dialogVisible = true"
        >创建项目</el-button
      >
      <hr />
      <el-pagination
        v-model="currentPage"
        :page-size="pageSize"
        :total="totalProjects"
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
      ></el-pagination>
      <el-divider></el-divider>
      <div class="scroll-container">
        <el-card
          class="project-card fengye7Font"
          v-for="project in displayedProjects"
          :key="project.id"
          @click="selectProject(project)"
        >
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
    <el-dialog v-model="dialogVisible" title="创建项目">
      <el-form :model="projectInfo" label-width="auto" style="max-width: 600px">
        <el-form-item label="项目名">
          <el-input v-model="projectInfo.name" />
        </el-form-item>
        <el-form-item label="管理者">
          <el-input
            v-model="projectInfo.manager"
            placeholder="请输入项目管理者"
          ></el-input>
        </el-form-item>
        <el-form-item label="日期">
          <el-date-picker
            v-model="projectInfo.date"
            type="date"
            placeholder="Pick a date"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="项目源">
          <el-input
            v-model="projectInfo.resource"
            placeholder="输入项目源地址(可选)"
          ></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="projectInfo.description" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="createProject">Create</el-button>
          <el-button @click="dialogVisible = false">Cancel</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from "vue";
import { useStore } from "vuex";
import { ElCard, ElDivider, ElButton, ElDialog } from "element-plus";
import axios from "axios";

const store = useStore();
const selectedProject = computed(() => store.state.project);
const baseURL = process.env.VUE_APP_API_DatabaseServer_URL;

const dialogVisible = ref(false); // 控制项目创建对话框显示与隐藏
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
import { onMounted } from 'vue';
onMounted(() => {
  getProjects();
});

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
    dialogVisible.value = false; // 关闭创建项目对话框
  } catch (error) {
    console.error("Failed to create project:", error);
    alert("创建项目失败，API请求失败!!!");
  }
};


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
  font-family:'Courier New', Courier, monospace;
  color: rgb(43, 43, 123);
  font-size: 1.5em;
}

.right-card {
  width: 60%;
  margin-left: 5px;
}

.scroll-container {
  max-height: 60vh; /* 设置最大高度，超出时显示滚动条 */
  overflow-y: auto; /* 垂直滚动条 */
}

.project-card {
  cursor: pointer;
  transition: background-color 0.3s; /* 添加过渡效果 */
  border-radius: 10px;
  margin: 10px;
  padding: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.project-card:hover {
  background-color: #3ed0ea; /* 悬停时的背景颜色 */
}

.project-card:active {
  transform: translateY(2px); /* 点击时的垂直位移效果 */
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1); /* 点击时的阴影效果 */
}
</style>
