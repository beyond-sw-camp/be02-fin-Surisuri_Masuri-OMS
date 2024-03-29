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
              <tr v-for="(order, index) in filteredOrders" :key="index">
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
      searchQuery: "", // 검색어를 저장할 변수 추가
    };
  },
  async mounted() {
    try {
      const token = sessionStorage.getItem("token");

      const response = await axios.get("http://121.140.125.34:11114/api/orders/list", {
        params: {
          page: 1,
          size: 5,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }); // 서버에서 주문 상세 정보 받아오기

      // 중복된 주문번호 제거
      const uniqueOrders = response.data.result.reduce((acc, curr) => {
        if (!acc[curr.merchantUid]) {
          acc[curr.merchantUid] = curr;
        }
        return acc;
      }, {});

      // 중복을 제거한 유일한 주문번호로 필터링하여 ordersDetailResult 업데이트
      this.ordersDetailResult = Object.values(uniqueOrders);
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
