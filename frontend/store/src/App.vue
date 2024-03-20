<template class="sb-nav-fixed">
  <div class="main-content">
    <!-- 조건부 렌더링을 통해 특정 경로들에서 SideComponent 숨김 -->
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
import SideComponent from "./components/SideComponent.vue";
import FooterComponent from "./components/FooterComponent.vue";

export default {
  name: "App",
  components: {
    HeaderComponent,
    SideComponent,
    FooterComponent,
  },
  data() {
    return {
      hiddenRoutes: ['/', '/password', '/register' , '/loginReset', '/passwordReset/:idx'],
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

:root {
  --bs-primary: #ffd54f; /* 더 어두운 노란색으로 설정 */
  --bs-primary-hover: #ffa726; /* hover 상태에서 사용할 주황색 */
}

.btn-primary {
  background-color: #00704a; /* 스타벅스 로고의 진한 녹색 */
  border-color: #00704a; /* 스타벅스 로고의 진한 녹색 */
}

.btn-primary:hover {
  background-color: #004d33; /* 로고 외곽의 더 어두운 녹색 */
  border-color: #004d33; /* 로고 외곽의 더 어두운 녹색 */
}

a {
  color: #004d33; /* 링크 색상도 hover와 일관성 있도록 조정 */
}
.right-side-container {
 background-color: rgba(246, 245, 239, 255);
 min-height: 100vh;
}
</style>
