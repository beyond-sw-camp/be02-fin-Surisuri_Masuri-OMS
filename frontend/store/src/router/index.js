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
    path: '/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/password',
    name: 'password',
    component: PasswordView
  },
  {
    path: '/loginreset',
    name: 'loginreset',
    component: ResetLoginView,
  },
  {
    path: '/passwordreset',
    name: 'passwordReset',
    component: ResetPasswordView,
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/userprofile',
    name: 'userprofile',
    component: UserProfileView
  },
  {
    path: '/product',
    name: 'product',
    component: ProductView
  },
  {
    path: '/orderdetail',
    name: 'orderdetail',
    component: OrderDetailView
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
