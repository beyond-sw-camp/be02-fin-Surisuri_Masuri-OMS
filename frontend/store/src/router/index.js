import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue';
import PasswordView from '../views/PasswordView.vue';
import ResetPasswordView from '../views/ResetPasswordView.vue';
import RegisterView from '../views/RegisterView.vue';
import ProductView from '../views/ProductView.vue';
import OrderDetailView from '../views/OrderDetailView.vue';
import NofiView from '../views/NofiView.vue';
import NofiDetailView from '@/views/NofiDetailView.vue';
import InquiryList from '../views/InquiryList.vue';
import InquiryDetail from '../views/InquiryDetail.vue';
import ResetLoginView from '../views/ResetLoginView.vue';
import UserProfileView from '../views/UserProfileView.vue';
import StockEditView from '../views/StockEditView.vue';



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
    path: '/password', // 로그인 페이지의 경로
    name: 'password',
    component: PasswordView // 로그인 컴포넌트
  },
  {
    path: '/login-reset',
    name: 'loginreset',
    component: ResetLoginView, // 수정된 부분
  },
  {
    path: '/password-reset',
    name: 'passwordReset',
    component: ResetPasswordView, // 수정된 부분
  },
  {
    path: '/register', // 로그인 페이지의 경로
    name: 'register',
    component: RegisterView // 로그인 컴포넌트
  },
  {
    path: '/userprofile', // 로그인 페이지의 경로
    name: 'userprofile',
    component: UserProfileView // 로그인 컴포넌트
  },
  {
    path: '/product', // 로그인 페이지의 경로
    name: 'product',
    component: ProductView // 로그인 컴포넌트
  },
  {
    path: '/orderdetail', // 로그인 페이지의 경로
    name: 'orderdetail',
    component: OrderDetailView // 로그인 컴포넌트
  },
  {
    path: '/nofi',
    name: 'NofiView',
    component: NofiView,
},
  {
    path: '/nofi/:id',
    name: 'NofiDetailView', // 이 이름을 공지사항 목록 컴포넌트에서 사용해야 합니다.
    component: NofiDetailView,
    props: true,
  },
  {
    path: '/inquiry',
    name: 'InquiryList',
    component: InquiryList,
  },
  {
    path: '/inquiry/new',
    name: 'NewInquiry',

  },
  {
    path: '/inquiry/:id',
    name: 'InquiryDetail',
    component: InquiryDetail,
    props: true,
  },
  {
    path: '/stockedit',
    name: 'stockedit',
    component: StockEditView,
    props: true,
  },
  

  // 여기에 다른 라우트를 추가할 수 있습니다.
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
