<template>
  <main>
    <div class="container-fluid px-4">
      <div class="card mb-4">
        <div class="card-header">
          <i class="fas fa-table me-1"></i>
          Products
          <input type="text" v-model="searchQuery" placeholder="상품 검색..." class="form-control w-25 float-end" />
        </div>
        <div class="card-body">
          <table id="productTable" class="table">
            <thead>
              <tr>
                <th>상품이름</th>
                <th>가격</th>
                <th>현재 재고수량</th>
                <th>구매 수량</th>
                <th>장바구니 추가</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(product, index) in products" :key="index">
                <td>{{ product.productName }}</td>
                <td>{{ product.price }}</td>
                <td></td>
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
                  <button @click="addToCart(product)" class="btn btn-success">장바구니에 추가</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <CartButtonComponent :cartItemCount="cartItemCount" />
  </main>
</template>

<script>
import axios from "axios";
import CartButtonComponent from "@/components/CartButtonComponent.vue";

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
    };
  },
  mounted() {
    this.fetchProducts(); // 페이지가 로드될 때 제품 데이터를 가져옵니다.
    this.fetchOrders();
  },
  methods: {
    async fetchProducts() {
      try {
        const token = sessionStorage.getItem("token");
        const response = await axios.get("http://localhost:8080/product/list", {
          params: {
            page: 1,
            size: 5,
          },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        this.products = response.data.result; // 서버로부터 받은 데이터를 products에 할당합니다.
      } catch (error) {
        console.error("상품 데이터를 가져오는 중 오류가 발생했습니다:", error);
      }
    },
    async fetchOrders() {
      try {
        const token = sessionStorage.getItem("token");
        // GET 요청을 보내고 응답을 변수에 저장합니다.
        const response = await axios.get("http://localhost:8080/orders/list", {
          params: {
            page: 1,
            size: 5,
          },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        // 서버로부터 받은 데이터를 변수에 저장합니다.
        const data = response.data.result;

        // 데이터를 콘솔에 출력합니다.
        console.log("데이터:", data);
      } catch (error) {
        // 오류가 발생한 경우 오류 메시지를 콘솔에 출력합니다.
        console.error("데이터를 불러오는 중 오류 발생:", error);
      }
    },
    async addToCart(product) {
      try {
        const token = sessionStorage.getItem("token");
        // CartCreateReq 객체 생성
        var CartCreateReq = {
          idx: null,
          productIdx: product.productIdx,
          productQuantity: product.purchaseQuantity,
        };

        // axios를 사용하여 POST 요청을 보냅니다.
        const response = await axios.post("http://localhost:8080/cart/addcart", CartCreateReq, {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        });

        // 서버로부터 응답을 받은 경우
        console.log("장바구니에 제품이 추가되었습니다:", response.data);
        // 성공적으로 장바구니에 제품이 추가되었음을 사용자에게 알립니다.
        alert(`${product.productName} ${product.purchaseQuantity}개가 장바구니에 추가되었습니다.`);
        // 카트 아이템 카운트를 업데이트합니다.
        this.cartItemCount += product.purchaseQuantity;
      } catch (error) {
        // 오류가 발생한 경우
        console.error("장바구니에 제품을 추가하는 중 오류가 발생했습니다:", error);
        // 오류 메시지를 사용자에게 알립니다.
        alert("장바구니에 제품을 추가하는 중 오류가 발생했습니다.");
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

