import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '/stores/userStore';

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
import NotFoundView from '../views/NotFoundView.vue';


const routes = [
  {
    path: '/home',
    name: 'home',
    component: HomeView,
    meta: { requiresAuth: true }
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
    component: UserProfileView,
    meta: { requiresAuth: true }
  },
  {
    path: '/product',
    name: 'product',
    component: ProductView,
    meta: { requiresAuth: true }
  },
  {
    path: '/orderdetail',
    name: 'orderdetail',
    component: OrderDetailView,
    meta: { requiresAuth: true }
  },
  {
    path: '/notice',
    name: 'NoticeView',
    component: NoticeView,
    meta: { requiresAuth: true }
},
  {
    path: '/noticedetail',
    name: 'NoticeDetail',
    component: NoticeDetailView,
    props: (route) => ({ query: route.query }),
    meta: { requiresAuth: true }
  },
  {
    path: '/question',
    name: 'QuestionList',
    component: QuestionList,
    meta: { requiresAuth: true }
  },
  {
    path: '/question/new',
    name: 'QuestionNew',
    component: QuestionNew,
    meta: { requiresAuth: true }
  },
  {
    path: '/questiondetail/:idx?', // :idx가 필수 파라미터로 설정되어 있음
    name: 'QuestionDetail',
    component: QuestionDetail,
    meta: { requiresAuth: true }
    
  },
  {
    path: '/stockedit',
    name: 'stockedit',
    component: StockEditView,
    props: true,
    meta: { requiresAuth: true }
  },
  {
    path: '/cart',
    name: 'cart',
    component: CartView,
    props: true,
    meta: { requiresAuth: true }
  },
  {
    path: '/:catchAll(.*)*', // 정의되지 않은 모든 경로를 위한 와일드카드 라우트
    name: 'NotFound',
    component: NotFoundView,
    meta: { requiresAuth: true }
  },

  // 여기에 다른 라우트를 추가할 수 있습니다.
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore();
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  const isAuthenticated = userStore.token; // Pinia 스토어에서 token 상태를 가져옵니다.

  if (requiresAuth && !isAuthenticated) {
    // 인증이 필요하고, 사용자가 인증되지 않은 경우 로그인 페이지로 리다이렉트
    next({ name: 'login' }); // 로그인 페이지의 name 또는 경로로 수정하세요.
  } else {
    // 그 외의 경우(인증이 필요 없거나 이미 인증된 경우), 페이지 이동 허용
    next();
  }
});

export default router
