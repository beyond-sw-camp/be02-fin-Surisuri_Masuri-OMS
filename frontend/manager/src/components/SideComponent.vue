<template>
  <div class ="sidebar">
  
    <div>
      <div class="brand-logo d-flex align-items-center justify-content-between">
        <router-link to="/home" class="text-nowrap logo-img">
          <img src="../assets/union.png" width="70%" alt="">
        </router-link>
        <div class="close-btn d-xl-none d-block sidebartoggler cursor-pointer" id="sidebarCollapse">
        </div>
      </div>
      
      <nav class="sidebar-nav scroll-sidebar" data-simplebar="">
        <ul id="sidebarnav">
          
          <li class="sidebar-item">
            <router-link class="sidebar-link" to="/home">
              <span>
                <i class="ti ti-layout-dashboard"></i>
              </span>
              <span class="hide-menu">Dashboard</span>
            </router-link>
          </li>
          
          <li class="sidebar-item">
            <router-link class="sidebar-link" to="/shop">
              <span>
                <i class="ti ti-home"></i>
              </span>
              <span class="hide-menu">가맹점 조회</span>
            </router-link>
          </li>
          <li class="sidebar-item">
            <router-link class="sidebar-link" to="/store">
              <span>
                <i class="ti ti-shopping-cart"></i>
              </span>
              <span class="hide-menu">주문 내역</span>
            </router-link>
          </li>
          <li class="sidebar-item">
            <router-link class="sidebar-link" to="/container">
              <span>
                <i class="ti ti-package"></i>
              </span>
              <span class="hide-menu">창고</span>
            </router-link>
          </li>
          <li class="sidebar-item">
            <router-link class="sidebar-link" to="/notice">
              <span>
                <i class="ti ti-file-description"></i>
              </span>
              <span class="hide-menu">공지사항</span>
            </router-link>
          </li>
          <li class="sidebar-item">
            <router-link class="sidebar-link" to="/inquiries">
              <span>
                <i class="ti ti-typography"></i>
              </span>
              <span class="hide-menu">문의사항</span>
            </router-link>
          </li>
        </ul>
      </nav>
      <!-- End Sidebar navigation -->
    </div>
  
</div>
</template>

<script></script>

<style scoped>
.left-sidebar {
  height: 100vh;
  min-width: 18%;
  z-index: 1000;
  position: relative;
}

.left-sidebar a {
  text-decoration: none; /* 링크의 밑줄 제거 */
  color: inherit;
  /* 링크 색상을 상위 요소의 색상과 동일하게 설정 */
}

.sidebar-item .hide-menu {
  font-size: 19px;
  color: #c6c6bd !important;
  
   /* 세부 목차 글씨 크기 조정 */
}

.nav-small-cap {
  font-size: 18px;
  margin-top: 30px;
  margin-bottom: 5px;
  
}

/* 세부 항목 글씨 크기 및 간격 조정 */
.sidebar-item {
 
  margin-top: 25px;
   /* 항목 사이의 간격 늘리기 */
}

.giga-coffee {
  width: 80px;
  height: 150px;
}

.left-sidebar .sidebar-item a:hover {
  background-color: #00704a !important;
   /* 앰버 색상, 노란색 계열 */
   /* 텍스트 색상을 흰색으로 변경 */
}
.left-sidebar {
  color: rgb(255, 255, 255) !important;
}
.sidebar {
  background-color: rgba(30, 57, 50, 255);
}
.ti {
  color: #e4e3cc !important;
  margin-left: 30px; /* 모든 ti 아이콘의 색상을 하얗게 설정 */
}
.text-nowrap { 
  margin-top: 10%;
  margin-left: 30%
  
}

</style>
