import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue';
import PasswordView from '../views/PasswordView.vue';
import ResetPasswordView from '../views/ResetPasswordView.vue';
import RegisterView from '../views/RegisterView.vue';
import ProductView from '../views/ProductView.vue';
import OrderDetailView from '../views/OrderDetailView.vue';
import NoticeView from '../views/NoticeView.vue';
import NoticeDetailView from '@/views/NoticeDetailView.vue';
import QuestionList from '../views/QuestionList.vue';
import QuestionDetail from '../views/QuestionDetail.vue';
import ResetLoginView from '../views/ResetLoginView.vue';
import UserProfileView from '../views/UserProfileView.vue';
import StockEditView from '../views/StockEditView.vue';
import CartView from '../views/CartView.vue';
import QuestionNew from '../views/QuestionNew.vue';



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
    path: '/loginReset',
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
    path: '/notice',
    name: 'NoticeView',
    component: NoticeView,
},
  {
    path: '/noticedetail',
    name: 'NoticeDetail',
    component: NoticeDetailView,
    props: (route) => ({ query: route.query })
  },
  {
    path: '/question',
    name: 'QuestionList',
    component: QuestionList,
  },
  {
    path: '/question/new',
    name: 'QuestionNew',
    component: QuestionNew,
  },
  {
    path: '/questiondetail/:idx?', // :idx가 필수 파라미터로 설정되어 있음
    name: 'QuestionDetail',
    component: QuestionDetail,
    // 다른 설정들...
  },
  {
    path: '/stockedit',
    name: 'stockedit',
    component: StockEditView,
    props: true,
  },
  {
    path: '/cart',
    name: 'cart',
    component: CartView,
    props: true,
  },
  

  // 여기에 다른 라우트를 추가할 수 있습니다.
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
