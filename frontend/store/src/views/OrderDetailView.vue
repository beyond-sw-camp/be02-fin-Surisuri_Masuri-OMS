<template>
  <main>
    <div class="container-fluid px-4">
      <h1 class="mt-4">주문 내역</h1>
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
      </div>
      <div class="card mb-4">
        <div class="card-header">
          <i class="fas fa-table me-1"></i>
          주문 내역
        </div>
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
              <tr v-for="(order, index) in ordersDetailResult" :key="index">
                <td>{{ order.createdDate }}</td>
                <td>{{ order.merchantUid }}</td>
                <td>
                  <div>{{ order.productDtoRes.productName }} ({{ order.productDtoRes.productQuantity }}개)</div>
                </td>
                <td>{{ order.totalPrice }}</td>
                <td>{{ order.deliveryStatus }}</td>
              </tr>
            </tbody>
          </table>
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
      ordersDetailResult: [], // 주문 상세 정보를 저장할 배열
      totalOrders: 0,
      todayOrders: 0,
      shippingOrders: 0,
    };
  },
  async mounted() {
    try {
      const token = sessionStorage.getItem("token");

      const response = await axios.get("http://localhost:8080/orders/list", {
        params: {
          page: 1,
          size: 5,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }); // 서버에서 주문 상세 정보 받아오기
      this.ordersDetailResult = response.data.result; // 받아온 정보를 ordersDetailResult에 저장

      // 전체 주문 수 설정
      this.totalOrders = this.ordersDetailResult.length;

      // 오늘 주문 수 설정
      const today = new Date();
      const todayStr = today.toISOString().slice(0, 10);
      this.todayOrders = this.ordersDetailResult.filter((order) => order.createdDate.slice(0, 10) === todayStr).length;

      // 배송 중인 주문 수 설정
      this.shippingOrders = this.ordersDetailResult.filter((order) => order.deliveryStatus === "배송 중").length;
    } catch (error) {
      console.error("Error fetching orders detail:", error);
    }
  },
};
</script>

<style scoped>
/* 필요한 스타일 추가 */
</style>
