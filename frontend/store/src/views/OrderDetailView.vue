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
                                <th>취소</th> <!-- 여기에 취소 칸을 추가 -->
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="order in orders" :key="order.id">
                                <td>{{ order.date }}</td>
                                <td>{{ order.number }}</td>
                                <td>
                                    <div v-for="product in order.products" :key="product.id">
                                        {{ product.name }} ({{ product.quantity }}개)
                                    </div>
                                </td>
                                <td>{{ order.totalAmount }}</td>
                                <td>
                                    <!-- 취소 버튼 추가, btn-danger 클래스 사용 -->
                                    <button type="button" class="btn btn-danger">취소</button>
                                </td>
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
    data() {
        return {
            orders: [
                {
                    id: 1,
                    date: '2024-03-10',
                    number: 'ORD001',
                    products: [
                        { id: 1, name: '상품 A', quantity: 2 },
                        { id: 2, name: '상품 B', quantity: 1 },
                    ],
                    totalAmount: '30,000원'
                },
                {
                    id: 2,
                    date: '2024-03-09',
                    number: 'ORD002',
                    products: [
                        { id: 3, name: '상품 C', quantity: 1 },
                        { id: 4, name: '상품 D', quantity: 3 },
                    ],
                    totalAmount: '110,000원'
                }
            ],
            totalOrders: 2,
            todayOrders: 1,
            shippingOrders: 1,
        }
    },
    mounted() {
        // 실제 애플리케이션에서는 이 부분에서 서버로부터 데이터를 불러오는 로직을 구현합니다.
        // 예시에서는 정적 데이터를 사용합니다.
    }
}
</script>

<style scoped>
/* 필요한 스타일 추가 */
</style>
