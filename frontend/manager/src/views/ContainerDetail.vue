<template>
  <div class="container-fluid px-4">
    <div class="row">
      <div class="col">
        <div class="card">
          <div class="card-header">상품 목록</div>
          <div class="card-body">
            <table class="table">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">상품 이름</th>
                  <th scope="col">재고</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(container, index) in containerss" :key="container.containerIdx">
                  <th scope="row">{{ index + 1 }}</th>
                  <td>{{ container.productName }}</td>
                  <td>{{ container.productQuantity }}</td>
                </tr>
              </tbody>
            </table>
            <!-- 페이지네이션 관련 UI 제거 -->
            <a @click="goBack" class="btn btn-primary btn-sm">목록으로 돌아가기</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from "axios";

export default {
  data() {
    return {
      containerss: [],
      currentPage: 1,
      isLoading: false, // 데이터 로딩 중인지 여부
      hasMoreData: true, // 더 불러올 데이터가 있는지 여부
    };
  },
  methods: {
    async fetchData() {
      if (this.isLoading || !this.hasMoreData) return;
      this.isLoading = true;

      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.get(
          `http://121.140.125.34:11114/api/container/singlestock?containerIdx=${this.$route.params.containerIdx}`,
          {
            params: {
              page: this.currentPage,
              size: 10,
            },
            headers: {
              AccessToken: accessToken,
            },
          }
        );
        if (response.data.result.length > 0) {
          this.containerss = [...this.containerss, ...response.data.result];
          this.currentPage++;
        } else {
          this.hasMoreData = false;
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        this.isLoading = false;
      }
    },
    checkScroll() {
      const nearBottom = window.innerHeight + window.scrollY >= document.body.offsetHeight - 10;
      if (nearBottom) {
        this.fetchData();
      }
    },
    goBack() {
      this.$router.go(-1);
    },
  },
  mounted() {
    this.fetchData();
    window.addEventListener('scroll', this.checkScroll);
  },
  beforeUnmount() { // 여기를 수정했습니다
    window.removeEventListener('scroll', this.checkScroll);
  },
};
</script>