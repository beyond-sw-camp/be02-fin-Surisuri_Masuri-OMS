<template>
  <div>
    <div class="container mt-5">
      <div class="row">
        <div class="col-auto">
          <input
            type="text"
            v-model="searchQuery"
            placeholder="가맹점 검색"
            class="form-control"
          />
        </div>
        <div class="col-auto">
          <button class="btn btn-primary" @click="searchShops">검색</button>
        </div>
      </div>
      <table class="table mt-3">
        <thead>
          <tr>
            <th scope="col">가맹점 이름</th>
            <th scope="col">주소</th>
            <th scope="col">UUID</th>
            <th scope="col">전화번호</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="shop in filteredShops" :key="shop.storeUuid">
            <td>{{ shop.storeName }}</td>
            <td>{{ shop.storeAddr }}</td>
            <td>{{ shop.storeUuid }}</td>
            <td>{{ shop.userPhoneNo }}</td>
          </tr>
        </tbody>
      </table>
      <nav aria-label="Page navigation">
        <ul class="pagination justify-content-end">
          <li class="page-item">
            <a
              class="page-link"
              href="#"
              aria-label="Previous"
              @click.prevent="prevRange"
            >
              <span aria-hidden="true">&laquo;</span>
            </a>
          </li>
          <li
            class="page-item"
            v-for="page in pageRange"
            :key="page"
            :class="{ active: page === currentPage }"
          >
            <a class="page-link" href="#" @click.prevent="fetchShops(page)">{{
              page
            }}</a>
          </li>
          <li class="page-item">
            <a
              class="page-link"
              href="#"
              aria-label="Next"
              @click.prevent="nextRange"
            >
              <span aria-hidden="true">&raquo;</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "ShopView",
  data() {
    return {
      searchQuery: "",
      shops: [], // 빈 배열로 초기화
      currentPage: 1,
      totalPages: 20, // 실제 서버로부터 받아온 총 페이지 수입니다.
      pageRangeSize: 5, // 한 번에 보여줄 페이지 수입니다.
    };
  },
  computed: {
    pageRange() {
      const startPage =
        Math.floor((this.currentPage - 1) / this.pageRangeSize) *
          this.pageRangeSize +
        1;
      const endPage = Math.min(
        startPage + this.pageRangeSize - 1,
        this.totalPages
      );
      return Array.from(
        { length: endPage - startPage + 1 },
        (_, i) => startPage + i
      );
    },
    filteredShops() {
      // shops가 정의되지 않은 상태를 방지하기 위해 초기화된 배열 사용
      return this.shops
        ? this.shops.filter((shop) =>
            shop.storeName
              .toLowerCase()
              .includes(this.searchQuery.toLowerCase())
          )
        : [];
    },
  },
  created() {
    this.fetchShops(this.currentPage);
  },

  methods: {
    async searchShops() {
      this.currentPage = 1; // 검색 시작 시 항상 첫 페이지로
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.get(
          "http://121.140.125.34:11114/api/store/search", // 검색 API 엔드포인트 주소 확인 필요
          {
            headers: {
              AccessToken: accessToken,
              
            },
            params: {
              storeName: this.searchQuery, // 백엔드와 일치하는 검색 파라미터 이름 사용
              page: this.currentPage,
              size: 5,
            },
          }
        );

        this.shops = response.data.result || []; // 검색 결과 업데이트
      } catch (error) {
        console.error("가맹점 검색 오류:", error);
        this.shops = [];
      }
    },

    async fetchShops(page) {
      this.currentPage = page;
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.get(
          "http://121.140.125.34:11114/api/store/list",
          {
            headers: {
              AccessToken: accessToken, // 'Authorization' 대신 'AccessToken' 사용
            },
            params: {
              page: this.currentPage,
              size: 5,
              searchQuery: this.searchQuery,
            },
          }
        );
        console.log(accessToken);
        // 서버 응답에 따라 .data 구조 조정 필요
        this.shops = response.data.result || []; // 응답이 없을 경우 빈 배열 할당
        console.log("가맹점 조회 응답:", response.data);
      } catch (error) {
        console.error("가맹점 조회 오류:", error);
        this.shops = []; // 오류 발생 시 shops를 빈 배열로 초기화
        // 오류 메시지 추출 및 콘솔에 출력
        const errorMessage =
          error.response && error.response.data && error.response.data.error
            ? error.response.data.error
            : "가맹점 조회 중 오류가 발생했습니다.";
        console.error("오류 메시지:", errorMessage);
        // 여기서 오류 메시지를 사용자에게 알리기 위한 로직을 추가할 수 있습니다.
        // 예: alert(errorMessage); 또는 상태 변수에 저장하여 템플릿에 표시
      }
    },
    nextRange() {
      const maxPage =
        Math.ceil(this.currentPage / this.pageRangeSize) * this.pageRangeSize;
      if (maxPage < this.totalPages) {
        this.fetchShops(maxPage + 1);
      }
    },
    prevRange() {
      const startPage =
        Math.floor((this.currentPage - 1) / this.pageRangeSize) *
        this.pageRangeSize;
      if (startPage >= this.pageRangeSize) {
        this.fetchShops(startPage - this.pageRangeSize + 1);
      }
    },
  },
};
</script>

<style scoped>
/* 여기에 추가 스타일을 넣으세요. */
</style>
