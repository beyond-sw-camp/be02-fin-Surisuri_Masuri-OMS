<template>
  <main>
    <div class="container-fluid px-4">
      <div class="card mb-4">
        <div class="card-header">
          <i class="fas fa-table me-1"></i>
          주문 내역
          <input type="text" v-model="searchQuery" placeholder="주문 검색..." class="form-control w-25 float-end" />
        </div>
        <div class="card-body">
          <table id="orderTable" class="table">
            <thead>
              <tr>
                <th>주문번호</th>
                <th>주문일</th>
                <th>결제금액</th>
                <th>배송상태</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(order, index) in ordersDetailResult" :key="index">
                <!-- 주문번호 클릭 이벤트 추가 -->
                <td @click="goToOrderDetail(order)" style="cursor: pointer">{{ order.merchantUid }}</td>
                <td>{{ order.createdDate }}</td>
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
    } catch (error) {
      console.error("Error fetching orders detail:", error);
    }
  },
  computed: {
    filteredOrders() {
      return this.ordersDetailResult.filter((order) =>
        order.merchantUid.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
    goToOrderDetail(order) {
      this.$router.push({ name: "StoreDetail", params: { merchantUid: order.merchantUid } });
    },
  },
};
</script>
