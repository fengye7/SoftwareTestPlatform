import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
  {
    path: "/",
    redirect: "/home", // 默认重定向到项目设置页面
  },
  {
    path: "/home",
    name: "Home",
    component: () => import("../views/HomeView.vue"), // 项目设置页面路由
  },
  {
    path: "/project-set",
    name: "ProjectSet",
    component: () => import("../views/ProjectSetView.vue"), // 项目设置页面路由
  },
  {
    path: "/:projectName/ordinary-test",
    name: "OrdinaryTest",
    component: () => import("../views/OrdinaryTestView.vue"), // 一般测试页面路由
  },
  {
    path: "/project-info",
    name: "ProjectInfo",
    component: () => import("../views/ProjectInfoView.vue"), // 项目介绍页面路由
  },
  {
    path: "/unit-test",
    name: "UnitTest",
    component: () => import("../views/UnitTestView.vue"), // 单元测试页面路由
  },
  {
    path: "/integration-test",
    name: "IntegrationTest",
    component: () => import("../views/IntegrationTestView.vue"), // 集成测试页面路由
  },
  {
    path: "/system-test",
    name: "SystemTest",
    component: () => import("../views/SystemTestView.vue"), // 系统测试页面路由
  },
  {
    path: "/property-test",
    name: "PropertyTest",
    component: () => import("../views/PropertyTestView.vue"), // 性能测试页面路由
  },
  {
    path: "/about",
    name: "About",
    component: () => import("../views/AboutThisWebView.vue"), // 关于本网站页面路由
  },
  // 通配符路由，匹配所有未知路径，指向 404 页面
  {
    path: "/:catchAll(.*)",
    name: "NotFound",
    component: () => import("../views/NotFoundView.vue"), // 404 页面路由
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
