<template>
  <main>
    <div class="container-fluid px-4">
      <div v-if="loading" class="text-center">로딩 중...</div>
      <div v-else class="card mb-4">
        <div class="card-header">주문 상세 정보</div>
        <div class="card-body">
          <div v-if="orderDetails">
            <p><strong>주문 번호:</strong> {{ orderDetails.merchantUid }}</p>
            <p><strong>주문 일시:</strong> {{ orderDetails.createdDate }}</p>
            <p><strong>주문 상태:</strong> {{ orderDetails.deliveryStatus }}</p>
            <p><strong>총 금액:</strong> {{ orderDetails.totalPrice }}</p>
            <p><strong>주문 품목:</strong></p>
            <ul>
              <li>{{ orderDetails.productDtoRes.productName }} - {{ orderDetails.productDtoRes.productQuantity }}개</li>
            </ul>
          </div>
          <div v-else>주문 상세 정보를 찾을 수 없습니다.</div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import axios from "axios";

export default {
  name: "OrderDetailView",
  data() {
    return {
      loading: true, // 로딩 상태
      orderDetails: {}, // 초기 상태는 비어있음
    };
  },
  created() {
    this.fetchOrderDetails();
  },
  methods: {
    async fetchOrderDetails() {
      this.loading = true;
      const merchantUid = this.$route.params.merchantUid;
      const token = sessionStorage.getItem("token");

      try {
        const response = await axios.get(`http://121.140.125.34:11114/api/orders/${merchantUid}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        this.orderDetails = response.data.result;
        console.log(this.orderDetails);
      } catch (error) {
        console.error("Error fetching order details:", error);
      }

      this.loading = false;
    },
  },
};
</script>
