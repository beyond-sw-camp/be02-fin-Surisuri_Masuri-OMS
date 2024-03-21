<template>
  <div class ="sidebar">
  
    <div>
      <div class="brand-logo d-flex align-items-center justify-content-between">
        <router-link to="/home" class="text-nowrap logo-img">
          <img src="../assets/union.png" width="50%" alt="" />
        </router-link>
        <div
          class="close-btn d-xl-none d-block sidebartoggler cursor-pointer"
          id="sidebarCollapse"
        ></div>
      </div>
      <!-- Sidebar navigation-->
      <nav
        data-v-2cf85266=""
        class="sidebar-nav scroll-sidebar"
        data-simplebar=""
      >
        <ul data-v-2cf85266="" id="sidebarnav">
          <li data-v-2cf85266="" class="nav-small-cap">
            <i data-v-2cf85266="" class="ti ti-dots nav-small-cap-icon fs-4"></i
            ><span data-v-2cf85266="" class="hide-menu"></span>
          </li>
          <li data-v-2cf85266="" class="sidebar-item">
            <a
              data-v-2cf85266=""
              href="/home"
              class="router-link-active router-link-exact-active sidebar-link"
              aria-current="page">
              <span data-v-2cf85266="">
              <i data-v-2cf85266="" class="ti ti-layout-dashboard"></i>
              </span>
              <span data-v-2cf85266="" class="hide-menu">HOME</span>
            </a>
          </li>
          <li data-v-2cf85266="" class="sidebar-item">
            <a data-v-2cf85266="" href="/product" class="sidebar-link"
              ><span data-v-2cf85266=""
                ><i data-v-2cf85266="" class="ti ti-article"></i></span
              ><span data-v-2cf85266="" class="hide-menu">상품발주</span></a
            >
          </li>
          <li data-v-2cf85266="" class="sidebar-item">
            <a data-v-2cf85266="" href="/orderdetail" class="sidebar-link"
              ><span data-v-2cf85266=""
                ><i data-v-2cf85266="" class="ti ti-wallet"></i></span
              ><span data-v-2cf85266="" class="hide-menu">주문내역</span></a
            >
          </li>
          <li data-v-2cf85266="" class="sidebar-item">
            <a data-v-2cf85266="" href="/stockedit" class="sidebar-link"
              ><span data-v-2cf85266=""
                ><i data-v-2cf85266="" class="ti ti-alert-circle"></i></span
              ><span data-v-2cf85266="" class="hide-menu">재고관리</span></a
            >
          </li>
          <li data-v-2cf85266="" class="sidebar-item">
            <a data-v-2cf85266="" href="/userprofile" class="sidebar-link"
              ><span data-v-2cf85266=""
                ><i data-v-2cf85266="" class="ti ti-cards"></i></span
              ><span data-v-2cf85266="" class="hide-menu">마이페이지</span></a
            >
          </li>
          <li data-v-2cf85266="" class="sidebar-item">
            <a data-v-2cf85266="" href="/notice" class="sidebar-link"
              ><span data-v-2cf85266=""
                ><i data-v-2cf85266="" class="ti ti-file-description"></i></span
              ><span data-v-2cf85266="" class="hide-menu">공지사항</span></a 
            >
          </li>
          <li data-v-2cf85266="" class="sidebar-item">
            <a data-v-2cf85266="" href="/question" class="sidebar-link"
              ><span data-v-2cf85266=""
                ><i data-v-2cf85266="" class="ti ti-typography"></i></span
              ><span data-v-2cf85266="" class="hide-menu">문의사항</span></a
            >
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
  margin-bottom: 20px;
   /* 항목 사이의 간격 늘리기 */
}

.giga-coffee {
  width: 80px;
  height: 150px;
}

.left-sidebar .sidebar-item a:hover {
  background-color: #00704a !important; /* 앰버 색상, 노란색 계열 */
   /* 텍스트 색상을 흰색으로 변경 */
}
.left-sidebar {
  color: rgb(255, 255, 255) !important;
}
.sidebar {
  background-color: rgba(30, 57, 50, 255);
}
.ti {
  color: #e4e3cc !important; /* 모든 ti 아이콘의 색상을 하얗게 설정 */
}
.text-nowrap { 
  margin-top: 10%;
  margin-left: 30%
  
}
</style>
