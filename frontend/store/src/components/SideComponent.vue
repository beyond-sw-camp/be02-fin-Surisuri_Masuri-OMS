<template>
    <aside class="left-sidebar">
      <!-- Sidebar scroll-->
      <div>
        <div class="brand-logo d-flex align-items-center justify-content-between">
          <router-link to="/home" class="text-nowrap logo-img">
            <img src="../assets/Group24.png" width="100%" alt="">
          </router-link>
          <div class="close-btn d-xl-none d-block sidebartoggler cursor-pointer" id="sidebarCollapse">
           
          </div>
        </div>
        <!-- Sidebar navigation-->
        <nav class="sidebar-nav scroll-sidebar" data-simplebar="">
          <ul id="sidebarnav">
            <li class="nav-small-cap">
              <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
              <span class="hide-menu">Home</span>
            </li>
            <li class="sidebar-item">
              <router-link class="sidebar-link" to="/home">
                <span>
                  <i class="ti ti-layout-dashboard"></i>
                </span>
                <span class="hide-menu">Dashboard</span>
              </router-link>
            </li>
            <li class="nav-small-cap">
              <i class="ti ti-dots nav-small-cap-icon fs-4"></i>
              <span class="hide-menu">가맹점 관리</span>
            </li>
            <li class="sidebar-item">
              <router-link class="sidebar-link" to="/product">
                <span>
                  <i class="ti ti-article"></i>
                </span>
                <span class="hide-menu">상품발주</span>
              </router-link>
            </li>
            <li class="sidebar-item">
              <router-link class="sidebar-link" to="/stockedit">
                <span>
                  <i class="ti ti-alert-circle"></i>
                </span>
                <span class="hide-menu">재고관리</span>
              </router-link>
            </li>
            <li class="sidebar-item">
              <router-link class="sidebar-link" to="/userprofile">
                <span>
                  <i class="ti ti-cards"></i>
                </span>
                <span class="hide-menu">마이페이지</span>
              </router-link>
            </li>
            <li class="sidebar-item">
              <router-link class="sidebar-link" to="/nofi">
                <span>
                  <i class="ti ti-file-description"></i>
                </span>
                <span class="hide-menu">공지사항</span>
              </router-link>
            </li>
            <li class="sidebar-item">
              <router-link class="sidebar-link" to="/inquiry">
                <span>
                  <i class="ti ti-typography"></i>
                </span>
                <span class="hide-menu">문의사항</span>
              </router-link>
              
            </li>
            <li class="sidebar-item">
              <router-link class="sidebar-link" to="/orderdetail">
                <span>
                  <i class="ti ti-typography"></i>
                </span>
                <span class="hide-menu">주문내역</span>
              </router-link>
              
            </li>
          </ul>
        </nav>
        <!-- End Sidebar navigation -->
      </div>
      
      
    </aside>
    
  </template>

<script>

</script>

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
    font-size: 19px; /* 세부 목차 글씨 크기 조정 */
}

.nav-small-cap {
    font-size: 18px;
    margin-top: 30px;
    margin-bottom: 30px;
}

/* 세부 항목 글씨 크기 및 간격 조정 */
.sidebar-item {
    margin-bottom: 20px; /* 항목 사이의 간격 늘리기 */
}

.giga-coffee {
    width: 80px;
    height: 150px;
}

</style>

