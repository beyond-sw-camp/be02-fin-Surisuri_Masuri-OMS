import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import ContainerList from '../views/ContainerList.vue';
import ContainerDetail from '@/views/ContainerDetail.vue';
import NoticeList from '../views/NoticeList.vue';
import NoticeDetail from '../views/NoticeDetail.vue';
import NoticeNew from '../views/NoticeNew.vue';
import ShopView from '../views/ShopView.vue';


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
{
  path: '/notice',
  name: 'NoticeList',
  component: NoticeList,
},
{
  path: '/notice/new',
  name: 'NoticeNew',
  component: NoticeNew,
},
{
  path: '/notice/:id',
  name: 'NoticeDetail',
  component: NoticeDetail,
  props: true,
},
{
  path: '/shop',
  name: 'shop',
  component: ShopView
},

  

  // 여기에 다른 라우트를 추가할 수 있습니다.
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
