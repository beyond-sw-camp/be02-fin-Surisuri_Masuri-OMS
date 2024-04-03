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
          <nav aria-label="Page navigation example ">
          <ul class="pagination justify-content-end">
            <li class="page-item" :class="{ disabled: pageGroupIndex === 0 }">
              <a class="page-link" href="#" @click="previousGroup">«</a>
            </li>
            <li class="page-item" v-for="page in pageGroup" :key="page" :class="{ active: currentPage === page }">
              <a class="page-link" href="#" @click="changePage(page)">{{ page }}</a>
            </li>
            <li class="page-item">
              <a class="page-link" href="#" @click="nextGroup">»</a>
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
      ordersDetailResult: [], // 주문 상세 정보를 저장할 배열
      searchQuery: "", 
      currentPage: 1,
      pageGroupIndex: 0, 
      pageGroupSize: 5, 
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
    this.fetchOrders(this.currentPage);
  },
  computed: {
    filteredOrders() {
      return this.ordersDetailResult.filter((order) =>
        order.merchantUid.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    pageGroup() {
      let startPage = this.pageGroupIndex * this.pageGroupSize + 1;
      return Array.from({ length: this.pageGroupSize }, (_, i) => startPage + i);
    },
  },
  methods: {
    goToOrderDetail(order) {
      this.$router.push({ name: "StoreDetail", params: { merchantUid: order.merchantUid } });
    },
    async fetchOrders(page) {
      try {
        const token = sessionStorage.getItem("token");
        const response = await axios.get("http://121.140.125.34:11114/api/orders/list", {
          params: {
            page: page,
            size: 5,
          },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        
        // 중복 제거 및 데이터 처리 로직 유지
        const uniqueOrders = response.data.result.reduce((acc, curr) => {
          if (!acc[curr.merchantUid]) {
            acc[curr.merchantUid] = curr;
          }
          return acc;
        }, {});
        this.ordersDetailResult = Object.values(uniqueOrders);
        
      } catch (error) {
        console.error("Error fetching orders:", error);
      }
    },
    changePage(page) {
      this.currentPage = page;
      this.fetchOrders(page);
    },
    nextGroup() {
      this.pageGroupIndex++;
      this.changePage(this.pageGroup[0]); // 그룹의 첫 페이지로 이동
    },
    previousGroup() {
      if (this.pageGroupIndex > 0) {
        this.pageGroupIndex--;
        this.changePage(this.pageGroup[0]); // 그룹의 첫 페이지로 이동
      }
    },
  },
};
</script>
