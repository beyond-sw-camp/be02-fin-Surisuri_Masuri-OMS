<template>
  <main>
    <div class="container-fluid px-4">
      <div class="card mb-4">
        <div class="card-header">
  <div class="row">
    <div class="col-auto">
      <input
        type="text"
        v-model="searchQuery"
        placeholder="상품 검색..."
        class="form-control"
      />
    </div>
    <div class="col-auto">
      <button @click="searchProducts" class="btn btn-primary">검색</button>
    </div>
  </div>
</div>
        <div class="card-body">
          <table id="productTable" class="table">
            <thead>
              <tr>
                <th>상품이름</th>
                <th>가격</th>
                
                <th>구매 수량</th>
                <th>장바구니 추가</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(product, index) in products" :key="index">
                <td>{{ product.productName }}</td>
                <td>{{ product.price }}</td>
                
                <td>
                  <input
                    type="number"
                    v-model.number="product.purchaseQuantity"
                    min="1"
                    :max="product.stockQuantity"
                    class="purchase-input"
                  />
                </td>
                <td>
                  <button @click="addToCart(product)" class="btn btn-success">
                    장바구니에 추가
                  </button>
                </td>
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
                <a
                  class="page-link"
                  href="#"
                  @click.prevent="fetchProducts(page)"
                  >{{ page }}</a
                >
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
    </div>
    <CartButtonComponent :cartItemCount="cartItemCount" />
  </main>
</template>

<script>
import axios from "axios";
import CartButtonComponent from "@/components/CartButtonComponent.vue";
import { getErrorMessage } from "../utils/error.js";
import swal from 'sweetalert';

export default {
  name: "ProductView",
  components: {
    CartButtonComponent,
  },
  data() {
    return {
      cartItemCount: 0,
      searchQuery: "",
      products: [],
      orders: [],
      currentPage: 1,
      totalPages: 20, // 실제 서버로부터 받아온 총 페이지 수
      pageRangeSize: 5,
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
  },
  mounted() {
    this.fetchProducts(this.currentPage); // 초기 제품 데이터 로드
  },
  methods: {
    async searchProducts() {
      // 검색 쿼리 기반으로 제품을 검색하는 로직
      this.currentPage = 1; // 검색을 시작할 때 페이지를 초기화
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.get("http://121.140.125.34:11113/api/product/search", { // 백엔드 URL에 맞게 수정하세요
          params: {
            productName: this.searchQuery,
            page: 1,
            size: 10,
          },
          headers: {
            AccessToken: accessToken,
          },
        });
        this.products = response.data.result; // 응답에서 상품 목록을 가져와 저장
        // 응답 형식에 따라 .data 내의 경로는 조정될 수 있습니다.
      } catch (error) {
        console.error("상품 검색 중 오류 발생:", error);
      }
    },
    async fetchProducts(page) {
      this.currentPage = page;

      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.get("http://121.140.125.34:11113/api/product/list", {
          params: {
            page: this.currentPage,
            size: 5,
          },
          headers: {
            AccessToken: accessToken,
          },
        });
        this.products = response.data.result; // 서버로부터 받은 데이터를 products에 할당합니다.
      } catch (error) {
        console.error("상품 데이터를 가져오는 중 오류가 발생했습니다:", error);
      }
    },
    async fetchOrders() {
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        // GET 요청을 보내고 응답을 변수에 저장합니다.
        const response = await axios.get(
          "http://121.140.125.34:11113/api/orders/list",
          {
            params: {
              page: 1,
              size: 5,
            },
            headers: {
              AccessToken: accessToken,
            },
          }
        );

        // 서버로부터 받은 데이터를 변수에 저장합니다.
        const data = response.data.result;

        // 데이터를 콘솔에 출력합니다.
        console.log("데이터:", data);
      } catch (error) {
        // 오류가 발생한 경우 오류 메시지를 콘솔에 출력합니다.
        console.error("데이터를 불러오는 중 오류 발생:", error);
      }
    },
    nextRange() {
      const maxPage =
        Math.ceil(this.currentPage / this.pageRangeSize) * this.pageRangeSize;
      if (maxPage < this.totalPages) {
        this.fetchProducts(maxPage + 1); // 수정된 부분
      }
    },
    prevRange() {
      const startPage =
        Math.floor((this.currentPage - 1) / this.pageRangeSize) *
        this.pageRangeSize;
      if (startPage >= this.pageRangeSize) {
        this.fetchProducts(startPage - this.pageRangeSize + 1); // 수정된 부분
      }
    },
    async addToCart(product) {
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        var CartCreateReq = {
          idx: null,
          productIdx: product.productIdx,
          productQuantity: product.purchaseQuantity,
        };

        const response = await axios.post(
          "http://121.140.125.34:11113/api/cart/addcart",
          CartCreateReq,
          {
            headers: {
              AccessToken: accessToken,
              "Content-Type": "application/json",
            },
          }
        );

        // 서버로부터 응답을 받은 경우
        console.log("장바구니에 제품이 추가되었습니다:", response.data);
        swal(
          `${product.productName} ${product.purchaseQuantity}개가 장바구니에 추가되었습니다.`
        );
        this.cartItemCount += product.purchaseQuantity;
      } catch (error) {
        console.error(
          "장바구니에 제품을 추가하는 중 오류가 발생했습니다:",
          error
        );

        // getErrorMessage를 사용하여 오류 메시지 처리
        let errorMessage;
        // error.response가 존재하는지와 함께, error.response.data와 error.response.data.errorCode가 있는지 확인
        if (
          error.response &&
          error.response.data &&
          error.response.data.errorCode
        ) {
          errorMessage = getErrorMessage(error.response.data.errorCode);
        } else {
          // 오류에 대한 자세한 정보가 없을 경우 일반적인 오류 메시지 제공
          errorMessage =
            "장바구니에 제품을 추가하는 중 오류가 발생했습니다. 다시 시도해주세요.";
        }

        // 최종적으로 결정된 오류 메시지를 사용자에게 알림
        swal(errorMessage);
      }
    },
  
  },
};
</script>

<style scoped>
.table th,
.table td {
  text-align: center; /* 모든 셀의 텍스트를 가운데 정렬합니다 */
}

.purchase-input {
  width: 100%; /* 구매 수량 입력 필드 너비를 셀에 맞춥니다 */
  box-sizing: border-box; /* 패딩과 테두리를 너비에 포함시킵니다 */
}

/* 각 컬럼의 너비를 지정할 수 있습니다. 예시는 임의의 값으로, 실제 상황에 맞게 조정하세요 */
.table th:nth-child(1),
.table td:nth-child(1) {
  width: 20%;
}
.table th:nth-child(2),
.table td:nth-child(2) {
  width: 15%;
}
.table th:nth-child(3),
.table td:nth-child(3) {
  width: 20%;
}
.table th:nth-child(4),
.table td:nth-child(4) {
  width: 20%;
}
.table th:nth-child(5),
.table td:nth-child(5) {
  width: 25%;
}

.btn {
  margin: auto; /* 버튼을 셀 내부에서 가운데 정렬합니다 */
  display: block; /* 버튼을 블록 요소로 만들어 너비 전체를 사용하게 합니다 */
}
.table th,
.table td {
  text-align: center; /* 모든 셀의 텍스트를 가운데 정렬합니다 */
  vertical-align: middle; /* 셀의 내용을 수직 중앙에 정렬합니다 */
}
</style>
