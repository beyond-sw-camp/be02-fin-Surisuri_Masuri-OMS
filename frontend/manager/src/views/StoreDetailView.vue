<template>
    <main>
        <div class="container-fluid px-4">
            <div v-if="loading" class="text-center">
                로딩 중...
            </div>
            <div v-else class="card mb-4">
                <div class="card-header">
                    주문 상세 정보
                </div>
                <div class="card-body">
                    <!-- 주문 정보 및 주문한 품목 표시 -->
                    <!-- 생략 -->
                </div>
            </div>
        </div>
    </main>
</template>

<script>
export default {
    name: "OrderDetailView",
    data() {
        return {
            loading: true, // 로딩 상태
            orderDetails: {} // 초기 상태는 비어있음
        };
    },
    created() {
        this.fetchOrderDetails();
    },
    methods: {
        async fetchOrderDetails() {
            this.loading = true;
            const orderNumber = this.$route.params.orderNumber;
            // 서버로부터 주문 상세 정보를 가져오는 로직 구현
            // 예시: const response = await axios.get(`/api/orders/${orderNumber}`);
            // this.orderDetails = response.data;

            // 임시 데이터로 대체
            this.orderDetails = {
                orderNumber: orderNumber,
                orderDate: '2023-03-15',
                paymentAmount: '60,000원',
                deliveryStatus: '배송 중',
                items: [
                    { itemId: 'ITM001', name: '상품 A', quantity: 2, price: '20,000원' },
                    { itemId: 'ITM002', name: '상품 B', quantity: 1, price: '40,000원' },
                    // 추가 품목 데이터...
                ],
            };

            this.loading = false;
        }
    },
}
</script>
