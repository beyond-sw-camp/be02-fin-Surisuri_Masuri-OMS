import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue';
import StoreView from '../views/StoreView.vue';
import StoreDetailView from '../views/StoreDetailView';
import AdminInquiryList from '../views/AdminInquiryList.vue';
import AdminInquiryDetail from '../views/AdminInquiryDetail.vue';

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
    path: '/store', 
    name: 'storeview',
    component: StoreView // 로그인 컴포넌트
  },
  {
    path: '/order-detail/:orderNumber',
    name: 'StoreDetail',
    component: StoreDetailView,
    props: true // 주문 상세 정보를 표시할 컴포넌트
  },
  {
    path: '/inquiries',
    name: 'AdminInquiryList',
    component: AdminInquiryList,
  },
  {
    path: '/inquiries/:id',
    name: 'AdminInquiryDetail',
    component: AdminInquiryDetail,
    props: true, // props를 통해 파라미터를 컴포넌트에 전달할 수 있게 합니다.
  },
  // 여기에 다른 라우트를 추가할 수 있습니다.
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
