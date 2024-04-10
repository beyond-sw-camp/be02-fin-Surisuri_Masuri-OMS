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
            <nav aria-label="Page navigation">
              <ul class="pagination justify-content-end">
                <li class="page-item">
                  <a class="page-link" href="#" aria-label="Previous" @click.prevent="prevRange">
                    <span aria-hidden="true">&laquo;</span>
                  </a>
                </li>
                <li class="page-item" v-for="page in pageRange" :key="page" :class="{ active: page === currentPage }">
                  <a class="page-link" href="#" @click.prevent="fetchNotices(page)">{{ page }}</a>
                </li>
                <li class="page-item">
                  <a class="page-link" href="#" aria-label="Next" @click.prevent="nextRange">
                    <span aria-hidden="true">&raquo;</span>
                  </a>
                </li>
              </ul>
            </nav>
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
      totalPages: 20, // 실제 서버로부터 받아온 총 페이지 수입니다.
      pageRangeSize: 5,
    };
  },
  computed: {
    pageRange() {
      const startPage = Math.floor((this.currentPage - 1) / this.pageRangeSize) * this.pageRangeSize + 1;
      const endPage = Math.min(startPage + this.pageRangeSize - 1, this.totalPages);
      return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
    },
  },
  methods: {
    async fetchData(containerIdx, page) {
      this.currentPage = page;
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.get(
          `http://121.140.125.34:11114/api/container/singlestock?containerIdx=${containerIdx}`,
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
        this.containerss = response.data.result; // 받아온 데이터를 containerss에 저장
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },
    nextRange() {
      const maxPage = Math.ceil(this.currentPage / this.pageRangeSize) * this.pageRangeSize;
      if (maxPage < this.totalPages) {
        this.fetchNotices(maxPage + 1);
      }
    },
    prevRange() {
      const startPage = Math.floor((this.currentPage - 1) / this.pageRangeSize) * this.pageRangeSize;
      if (startPage >= this.pageRangeSize) {
        this.fetchNotices(startPage - this.pageRangeSize + 1);
      }
    },
    goBack() {
      this.$router.go(-1);
    },
  },
  mounted() {
    // 두 번째 템플릿이 마운트되면서 컨테이너의 idx 값을 받아와서 데이터를 불러옴
    const containerIdx = this.$route.params.containerIdx;
    this.fetchData(containerIdx);
  },
};
</script>
