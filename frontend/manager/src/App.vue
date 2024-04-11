<template class="sb-nav-fixed">
  <div class="main-content">
    <!-- 조건부 렌더링을 통해 특정 경로들에서 SideComponent 숨김 -->
    <LoadingComponent v-if="loadingStore.isLoading" />
    <SideComponent class="side-component" v-if="!isHiddenPage" />
    <div class="right-side-container">
      <HeaderComponent v-if="!isHiddenPage" />
      <div class="content-view">
        <router-view/>
      </div>
      <!-- 조건부 렌더링을 통해 특정 경로들에서 FooterComponent 숨김 -->
      <FooterComponent v-if="!isHiddenPage"></FooterComponent>
    </div>
  </div>
</template>


<script>
import HeaderComponent from "./components/HeaderComponent.vue";
import { useLoadingStore } from '../stores/loadingStore';
import SideComponent from "./components/SideComponent.vue";
import FooterComponent from "./components/FooterComponent.vue";
import LoadingComponent from './components/LoadingComponent.vue';

export default {
  name: "App",
  components: {
    HeaderComponent,
    SideComponent,
    FooterComponent,
    LoadingComponent,
  },
  setup() {
    const loadingStore = useLoadingStore();

    return {
      loadingStore, // Pinia 로딩 상태 스토어
    };
  },
  data() {
    return {
      hiddenRoutes: ['/', '/password', '/register', '/password-reset', '/login-reset'],
      isHiddenPage: false,
    };
  },
  watch: {
    // $route 객체를 감시
    '$route'(to) {
      // 경로 변경 시 isHiddenPage 값을 업데이트
      this.isHiddenPage = this.hiddenRoutes.includes(to.path);
    }
  },
  created() {
    this.isHiddenPage = this.hiddenRoutes.includes(this.$route.path);
  },
};
</script>


<style>
.side-component {
  height:auto;
  width: 18%;
}

.scrollable::-webkit-scrollbar {
  display: none;
}

/* Flexbox 사용 방식으로 .main-content 수정 */
.main-content {
  display: flex;
  flex-direction: row; /* 사이드바를 왼쪽에, 나머지 콘텐츠를 오른쪽에 배치 */
  width: 100%; /* 화면 전체 너비를 차지하도록 설정 */
}

.header-content-container {
  display: flex;
  flex-direction: column; /* Header above content */
  flex-grow: 1; /* Take up remaining space */
}
.right-side-container {
  display: flex;
  flex-direction: column; /* 콘텐츠를 세로로 정렬 */
  flex-grow: 1; /* 남은 공간을 모두 채우도록 설정 */
}
.content-view {
  flex-grow: 1; 
}
.mt-4 {
  margin-top: 2.0rem !important; /* Adjust as necessary */
}
/* SweetAlert 팝업의 전반적인 크기 조정 */
.swal-modal {
  width: 400px; /* 기본 크기보다 작게 조정 */
}

/* 내부 콘텐츠의 패딩 조정 */
.swal-modal .swal-text {
  font-size: 14px; /* 텍스트 크기 조정 */
  padding: 20px; /* 내부 패딩 조정 */
}

/* 버튼 크기 조정 */
.swal-modal .swal-button {
  padding: 7px 19px; /* 버튼의 패딩 조정 */
  font-size: 12px; /* 버튼 내 텍스트 크기 조정 */
}
</style>
