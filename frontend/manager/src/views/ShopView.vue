<template>
  <div>
    <div class="container mt-5">
      <div class="row">
        <div class="col">
          <input
            type="text"
            v-model="searchQuery"
            class="form-control search-input"
            placeholder="가맹점 검색"
          />
        </div>
        <div class="col-auto">
          <button class="btn btn-primary" @click="fetchShops">검색</button>
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
    async fetchShops(page) {
      this.currentPage = page;
      try {
        const token = sessionStorage.getItem("token");
        const response = await axios.get("http://121.140.125.34:11113/api/store/list", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {
            page: this.currentPage,
            size: 5,
            searchQuery: this.searchQuery,
          },
        });
        // 서버 응답에 따라 .data 구조 조정 필요
        this.shops = response.data.result || []; // 응답이 없을 경우 빈 배열 할당
        console.log("가맹점 조회 응답:", response.data);
      } catch (error) {
        console.error("가맹점 조회 오류:", error);
        this.shops = []; // 오류 발생 시 shops를 빈 배열로 초기화
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
  watch: {
    searchQuery() {
      // 검색어가 변경될 때마다 가맹점 다시 불러오기
      this.fetchShops();
    },
  },
 
};
</script>

<style scoped>
/* 여기에 추가 스타일을 넣으세요. */
</style>
