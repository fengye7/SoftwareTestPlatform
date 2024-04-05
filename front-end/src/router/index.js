import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/project-set', // 默认重定向到项目设置页面
  },
  {
    path: '/project-set',
    name: 'ProjectSet',
    component: () => import('../views/ProjectSetView.vue'), // 项目设置页面路由
  },
  {
    path: '/:projectName/ordinary-test',
    name: 'OrdinaryTest',
    component: () => import('../views/OrdinaryTestView.vue')// 一般测试页面路由
  },
  {
    path: '/:projectName/unit-test',
    name: 'UnitTest',
    component: () => import('../views/UnitTestView.vue')// 单元测试页面路由
  },
  // 通配符路由，匹配所有未知路径，指向 404 页面
  {
    path: '/:catchAll(.*)',
    name: 'NotFound',
    component: () => import('../views/NotFoundView.vue'), // 404 页面路由
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
