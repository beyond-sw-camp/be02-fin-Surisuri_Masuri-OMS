<template>
    <main>
        <div class="container-fluid px-4">
            <div class="card mb-4">
                <div class="card-header">
                    <i class="fas fa-table me-1"></i>
                    주문 내역
                    <input type="text" v-model="searchQuery" placeholder="주문 검색..." class="form-control w-25 float-end">
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
                                <td @click="goToOrderDetail(order.orderNumber)" style="cursor: pointer;">{{ order.orderNumber }}</td>
                                <td>{{ order.orderDate }}</td>
                                <td>{{ order.paymentAmount }}</td>
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
export default {
    name: "OrderHistoryView",
    data() {
        return {
            searchQuery: '',
            orders: [
                { 
                    id: 1, 
                    orderNumber: 'ORD001', 
                    orderDate: '2023-03-15', 
                    paymentAmount: '30,000원', 
                    deliveryStatus: '배송 중' 
                },
                // 추가 주문 데이터...
            ],
        }
    },
    computed: {
        filteredOrders() {
            return this.orders.filter(order =>
                order.orderNumber.toLowerCase().includes(this.searchQuery.toLowerCase())
            );
        }
    },
    methods: {
  goToOrderDetail(orderNumber) {
    this.$router.push({ name: 'StoreDetail', params: { orderNumber: orderNumber } });
  }
}
}
</script>
