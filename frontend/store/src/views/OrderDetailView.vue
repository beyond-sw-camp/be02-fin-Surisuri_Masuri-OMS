<template>
  <main>
    <div class="container-fluid px-4">
      <div class="row">
        <div class="col-xl-3 col-md-6">
          <div class="card bg-primary text-white mb-4">
            <div class="card-body">총 주문 수: {{ totalOrders }}</div>
          </div>
        </div>
        <div class="col-xl-3 col-md-6">
          <div class="card bg-warning text-white mb-4">
            <div class="card-body">금일 주문 수: {{ todayOrders }}</div>
          </div>
        </div>
        <div class="col-xl-3 col-md-6">
          <div class="card bg-success text-white mb-4">
            <div class="card-body">배송 중인 주문 수: {{ shippingOrders }}</div>
          </div>
        </div>
        <!-- 추가: 배송 전인 주문 수 -->
        <div class="col-xl-3 col-md-6">
          <div class="card bg-info text-white mb-4">
            <div class="card-body">배송 전인 주문 수: {{ nonShippingOrders }}</div>
          </div>
        </div>
      </div>
      <div class="card mb-4">
        
        <div class="card-body">
          <table class="table">
            <thead>
              <tr>
                <th>주문일</th>
                <th>주문번호</th>
                <th>상품 정보</th>
                <th>결제 금액</th>
                <th>배송 상태</th>
              </tr>
            </thead>
            <tbody>
              <!-- 주문 상세 정보를 반복하여 출력 -->
              <tr v-for="(order, index) in ordersDetailResult" :key="index">
                <td>{{ order.createdDate }}</td>
                <td>{{ order.merchantUid }}</td>
                <td>
                  <!-- 상품 정보 출력 -->
                  <div>{{ order.productDtoRes.productName }} ({{ order.productDtoRes.productQuantity }}개)</div>
                </td>
                <td>{{ order.totalPrice }}</td>
                <!-- 배송 상태에 따라 다르게 표시 -->
                <td v-if="order.deliveryStatus === '출고 처리'">
                  <span class="card bg-success text-center">{{ order.deliveryStatus }}</span>
                </td>
                <td v-if="order.deliveryStatus === '배송 완료'">
                  <span class="card bg-success text-center">{{ order.deliveryStatus }}</span>
                </td>
                <td v-if="order.deliveryStatus === '배송중'">
                  <span class="card bg-success text-center">{{ order.deliveryStatus }}</span>
                </td>
                <td v-else-if="order.deliveryStatus === '배송전'">
                  <span class="card bg-info text-center">{{ order.deliveryStatus }}</span>
                </td>
              </tr>
            </tbody>
          </table>
          <nav aria-label="Order pagination">
      <ul class="pagination justify-content-end">
        <li class="page-item" :class="{ disabled: currentPage <= 1 }">
          <button class="page-link" @click="prevPage" :disabled="currentPage <= 1">&laquo;</button>
        </li>
        <!-- 현재 페이지 표시, 선택적으로 다른 페이지 번호도 표시 가능 -->
        <li class="page-item active" aria-current="page">
          <span class="page-link">{{ currentPage }}</span>
        </li>
        <li class="page-item" :class="{ disabled: currentPage >= totalPages }">
          <button class="page-link" @click="nextPage" :disabled="currentPage >= totalPages">&raquo;</button>
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
      ordersDetailResult: [],
      totalOrders: 0,
      todayOrders: 0,
      shippingOrders: 0,
      nonShippingOrders: 0,
      currentPage: 1, // 현재 페이지 번호
      pageSize: 5, // 페이지 당 주문 수
      totalPages: 25, // 전체 페이지 수
    };
  },
  async mounted() {
    this.fetchOrders(); // 페이지 마운트 시 주문 목록 불러오기
  },
  methods: {
    async fetchOrders() {
      try {
        const token = sessionStorage.getItem("token");
        const response = await axios.get("http://121.140.125.34:11113/api/orders/list", {
          params: {
            page: this.currentPage,
            size: this.pageSize,
          },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        // 여기서 전체 주문 수와 전체 페이지 수 계산 (예: response에서 전체 주문 수 받아오기)
        this.totalPages = Math.ceil(response.data.totalCount / this.pageSize); // totalCount는 예시입니다.

        const uniqueOrderNumbers = new Set();
        const uniqueOrders = [];

        response.data.result.forEach((order) => {
          if (!uniqueOrderNumbers.has(order.merchantUid)) {
            uniqueOrderNumbers.add(order.merchantUid);
            uniqueOrders.push(order);
          }
        });

        this.ordersDetailResult = uniqueOrders;

        this.totalOrders = this.ordersDetailResult.length;
        this.calculateOrderStatistics(); // 주문 통계 계산을 별도의 메소드로 분리
      } catch (error) {
        console.error("Error fetching orders detail:", error);
      }
    },
    calculateOrderStatistics() {
      // 오늘 주문 수, 배송 중인 주문 수, 배송 전인 주문 수 계산하는 로직을 여기로 이동
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.fetchOrders();
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.fetchOrders();
      }
    },
  },
};
</script>


<style scoped>
/* 필요한 스타일 추가 */
</style>
