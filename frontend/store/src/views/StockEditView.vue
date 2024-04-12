  <template>
    <main>
      <div class="container-fluid px-4">
        <div class="card mb-4">

          <div class="card-body">
            <table id="stockTable" class="table">
              <thead>
                <tr>
                  <th>상품이름</th>
                  <th>현재 재고수량</th>
                  <th>재고수량 수정</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="product in stockList"
                  :key="product.storeStockDto.productName"
                >
                  <td>{{ product.storeStockDto.productName }}</td>
                  <td>{{ product.storeStockDto.productQuantity }}</td>
                  <td>
                    <!-- 여기에 재고수량 입력 필드와 수정 버튼을 추가 -->
                    <input
                      type="number"
                      v-model.number="product.productQuantity"
                      min="0"
                      class="stock-input"
                    />
                    <button
                      @click="updateProductQuantity(product)"
                      class="btn btn-primary"
                    >
                      수정
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
                    @click.prevent="fetchStockList(page)"
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
    </main>
  </template>

  <script>
  import axios from "axios";

  export default {
    data() {
      return {
        stockList: [],
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
    created() {
      this.fetchStockList(this.currentPage);
    },
    methods: {
      async fetchStockList(page) {
        this.currentPage = page;
        try {
          const accessToken = sessionStorage.getItem("accessToken");
          console.log("fetchStockList - 사용할 토큰:", accessToken);
          if (!accessToken) {
            console.error("토큰이 없습니다.");
            return;
          }

          const response = await axios.get(
            "http://121.140.125.34:11113/api/stock/list",
            {
              headers: {
                AccessToken: accessToken,
              },
              params: {
                page: this.currentPage,
                size: 10,
              },
            }
          );

          console.log("fetchStockList - 응답 데이터:", response.result); // 응답 데이터 확인

          this.stockList = response.data.result.map((product) => ({
            ...product,
            productQuantity: product.storeStockDto.productQuantity,
            idx: product.storeStockIdx,
          }));
          console.log("API 호출에 성공했습니다.");
          console.log("받은 리스트:", this.stockList);
        } catch (error) {
          console.error("API 호출 중 오류가 발생했습니다.", error);
        }
      },
      nextRange() {
        const maxPage = Math.ceil(this.currentPage / this.pageRangeSize) * this.pageRangeSize;
        if (maxPage < this.totalPages) {
          this.fetchStockList(maxPage + 1);
        }
      },
      prevRange() {
        const startPage = Math.floor((this.currentPage - 1) / this.pageRangeSize) * this.pageRangeSize;
        if (startPage >= this.pageRangeSize) {
          this.fetchStockList(startPage - this.pageRangeSize + 1);
        }
      },
      async updateProductQuantity(product) {
        try {
          const accessToken = sessionStorage.getItem("accessToken");
          console.log("updateProductQuantity - 사용할 토큰:", accessToken); // 토큰 정보 확인
          if (!accessToken) {
            console.error("토큰이 없습니다.");
            return;
          }

          console.log("updateProductQuantity - 요청 데이터:", {
            // 요청 데이터 확인
            idx: product.storeStockIdx,
            stockQuantity: product.productQuantity,
          });

          const response = await axios.patch(
            "http://121.140.125.34:11113/api/stock/update",
            {
              idx: product.storeStockIdx,
              stockQuantity: product.productQuantity,
            },
            {
              headers: {
                "Content-Type": "application/json",
                AccessToken: accessToken,
              },
            }
          );

          console.log("updateProductQuantity - 응답 데이터:", response.data); // 응답 데이터 확인

          if (response.status === 200) {
            // HTTP 상태 코드가 200인 경우 성공으로 간주
            console.log("재고 업데이트 성공");
            alert("재고 수정이 완료되었습니다.");
          } else {
            console.error(`재고 업데이트 실패: ${response.data.message}`);
          }
        } catch (error) {
          console.error("API 호출 중 오류가 발생했습니다.", error);
        }
      },
    },

  };
  </script>

  <style scoped>
  .stock-input {
    /* 전체 셀 너비의 70%에서 버튼과의 간격을 고려해 조정 */
    display: inline-block; /* 인라인 블록 요소로 설정하여 옆에 다른 요소(버튼)를 둘 수 있게 함 */
    margin-right: 10px; /* 입력 필드와 버튼 사이의 간격 조정 */
  }

  .btn {
    width: 15%; /* 버튼의 너비를 셀 너비의 30%로 설정 */
    display: inline-block; /* 인라인 블록 요소로 설정하여 옆에 다른 요소(입력 필드)와 나란히 배치 */
  }

  /* 테이블 셀의 텍스트를 가운데 정렬하려면 다음 CSS도 추가하세요 */
  .table th,
  .table td {
    text-align: center; /* 모든 셀의 텍스트를 가운데 정렬합니다 */
  }
  .table th,
  .table td {
    text-align: center; /* 모든 셀의 텍스트를 가운데 정렬합니다 */
    vertical-align: middle; /* 셀의 내용을 수직 중앙에 정렬합니다 */
  }
  </style>
