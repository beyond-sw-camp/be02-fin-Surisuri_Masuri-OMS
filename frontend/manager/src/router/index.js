import { createRouter, createWebHistory } from "vue-router";
import { useManagerStore } from "/stores/managerStores";

import HomeView from "../views/HomeView.vue";
import LoginView from "../views/LoginView.vue";
import StoreView from "../views/StoreView.vue";
import StoreDetailView from "../views/StoreDetailView.vue"; // 확장자 .vue를 추가해 주세요.
import QuestionList from "../views/QuestionList.vue";
import QuestionDetail from "../views/QuestionDetail.vue";
import ContainerList from "../views/ContainerList.vue";
import ContainerDetail from "@/views/ContainerDetail.vue"; // 상대 경로와 절대 경로가 혼용되어 사용되었습니다. 프로젝트 설정에 따라 수정하세요.
import NoticeList from "../views/NoticeList.vue";
import NoticeDetail from "../views/NoticeDetail.vue";
import NoticeNew from "../views/NoticeNew.vue";
import ShopView from "../views/ShopView.vue";
import NotFound from "../views/NotFound.vue";

const routes = [
  {
    path: "/home",
    name: "home",
    component: HomeView,
  },
  {
    path: "/", // 로그인 페이지의 경로
    name: "login",
    component: LoginView, // 로그인 컴포넌트
  },
  {
    path: "/store",
    name: "storeview",
    component: StoreView,
  },
  {
    path: "/order-detail/:merchantUid",
    name: "StoreDetail",
    component: StoreDetailView,
    props: true, // 주문 상세 정보를 표시할 컴포넌트
  },
  {
    path: "/question",
    name: "QuestionList",
    component: QuestionList,
  },
  {
    path: "/questiondetail/:idx?", // :idx가 필수 파라미터로 설정되어 있음
    name: "QuestionDetail",
    component: QuestionDetail,
    props: true, // props를 통해 파라미터를 컴포넌트에 전달할 수 있게 합니다.
  },
  {
    // 이 부분에서 수정이 필요했습니다. 쉼표가 누락되었었습니다.
    path: "/container",
    name: "ContainerList",
    component: ContainerList,
  },
  {
    path: "/ContainerDetail/:containerIdx", // 일관성을 위해 동적 파라미터 id를 추가했습니다.
    name: "ContainerDetail",
    component: ContainerDetail,
    props: true,
  },
  {
    path: "/notice",
    name: "NoticeList",
    component: NoticeList,
  },
  {
    path: "/noticenew",
    name: "NoticeNew",
    component: NoticeNew,
  },
  {
    path: "/noticedetail",
    name: "NoticeDetail",
    component: NoticeDetail,
    props: (route) => ({ query: route.query }),
  },
  {
    path: "/shop",
    name: "shop",
    component: ShopView,
  },
  {
    path: "/:catchAll(.*)*", // 정의되지 않은 모든 경로를 위한 와일드카드 라우트
    name: "NotFound",
    component: NotFound,
  },
  // 여기에 다른 라우트를 추가할 수 있습니다.
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  const managerStore = useManagerStore(); // Pinia store 인스턴스화
  const isAuthenticated = managerStore.token; // 로그인 상태 확인

  if (!isAuthenticated && to.name !== "login") {
    // 로그인 및 회원가입 페이지를 제외한 페이지 접근 제어
    // 로그인 페이지로 리다이렉트
    next({ name: "login" });
  } else {
    // 접근 허용
    next();
  }
});

export default router;
