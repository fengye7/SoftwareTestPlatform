<template>
  <div class="container">
    <!-- 左边的卡片 -->
    <el-card class="left-card">
      <h2>当前选择的项目信息</h2>
      <div v-if="selectedProject.name != '未设置项目'">
        <p>项目名称：{{ selectedProject.name }}</p>
        <p>项目管理者：{{ selectedProject.manager }}</p>
        <p>项目描述：{{ selectedProject.description }}</p>
        <p>项目创建时间：{{ selectedProject.date }}</p>
        <p v-show="selectedProject.resource !== ''">
          项目源地址：{{ selectedProject.resource }}
        </p>
        <el-button type="primary" @click="generateTestResults">测试</el-button>
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
          <el-button type="primary" @click="onSubmit">Create</el-button>
          <el-button @click="dialogVisible = false">Cancel</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
     <!-- 测试结果对话框 -->
    <el-dialog v-model="showTestResults" title="测试结果" width="70%">
      <el-table :data="testResults" style="width: 100%">
        <el-table-column prop="TestCaseID" label="TestCaseID" width="100" />
        <el-table-column prop="Input" label="Input" />
        <el-table-column prop="ExpectedOutput" label="ExpectedOutput" />
        <el-table-column prop="ActualOutput" label="ActualOutput" />
        <el-table-column prop="Correctness" label="Correctness" />
        <el-table-column prop="Time" label="Time" />
      </el-table>
        <template v-slot:footer>
        <el-button @click="showTestResults = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from "vue";
import { useStore } from "vuex";
import { ElCard, ElDivider, ElButton, ElDialog } from "element-plus";

const store = useStore();
const selectedProject = computed(() => store.state.project);

const dialogVisible = ref(false); // 控制项目创建对话框显示与隐藏
const projectInfo = reactive({
  name: "",
  description: "",
  date: "",
  manager: "",
  resource: "", // 项目若开源，可选url
});
const projects = ref([
  {
    name: "判断三角形",
    description:
      "输入三角形三边（0~800），程序返回三角形类别，输出有：等边三角形、等腰三角形、 普通三角形、不能构成三角形",
    date: "2024-04-05",
    manager: "fengye7",
    resource: "#############",
  },
  {
    name: "万年历",
    description: "输入日期（year:1800~2100）,判断日期是否合法",
    date: "2024-04-05",
    manager: "fengye7",
    resource: "",
  },
   {
    name: "电脑销售系统",
    description: "输入主机、显示器、外设数量（范围分别为0~70、0~80、0~90）,计算销售员应得的佣金",
    date: "2024-05-25",
    manager: "zyy",
    resource: "",
  },
   {
    name: "电信收费系统",
    description: "输入用户未按时缴费次数（0~11）和通话时长（0~单月最大分钟数）,计算本月最终通话费用",
    date: "2024-05-25",
    manager: "zyy",
    resource: "",
  },
  {
    name: "测试工具平台",
    description: "测试平台的各种功能：单元测试，系统测试，集成测试……",
    date: "2024-04-05",
    manager: "fengye7",
    resource: "#############",
  },
]);
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
  console.log("选中的项目：", project);
  store.commit("setProject", project);
};
const showTestResults = ref(false); // 控制测试结果显示与隐藏
const testResults = ref([]); // 存储测试结果

const generateTestResults = () => {
  // 获取当前时间并格式化
  const getCurrentDateTime = () => {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  };

  // 模拟生成测试结果
  testResults.value = [
    {
      TestCaseID: 1,
      Input: "1, 1, 1",
      ExpectedOutput: "等边三角形",
      ActualOutput: "等边三角形",
      Correctness: "正确",
      Time: getCurrentDateTime(),
    },
    {
      TestCaseID: 2,
      Input: "2, 2, 3",
      ExpectedOutput: "等腰三角形",
      ActualOutput: "等腰三角形",
      Correctness: "正确",
      Time: getCurrentDateTime(),
    },
    // 添加更多测试结果
  ];
  showTestResults.value = true;
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
  font-family: 'fengye7Font';
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
