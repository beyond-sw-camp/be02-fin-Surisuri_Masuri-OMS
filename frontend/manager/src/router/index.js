import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import ContainerList from '../views/ContainerList.vue';
import ContainerDetail from '@/views/ContainerDetail.vue';


const routes = [
  {
    path: '/home',
    name: 'home',
    component: HomeView
  },
  {
    path: '/', // 로그인 페이지의 경로
    name: 'login',
    component: LoginView // 로그인 컴포넌트
  },
  {
    path: '/container',
    name: 'ContainerList',
    component: ContainerList,
},
  {
    path: '/containerdetail',
    name: 'ContainerDetail',
    component: ContainerDetail,
},
  

  // 여기에 다른 라우트를 추가할 수 있습니다.
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
