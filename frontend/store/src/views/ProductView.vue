<template>
    <main>
        <div class="container-fluid px-4">
            
            <div class="card mb-4">
                <div class="card-header">
                    <i class="fas fa-table me-1"></i>
                    Products
                    <input type="text" v-model="searchQuery" placeholder="상품 검색..." class="form-control w-25 float-end">
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
                            <tr v-for="(product, index) in filteredProducts" :key="index">
                                <td>{{ product.name }}</td>
                                <td>{{ product.price }}</td>
                                <td>{{ product.stockQuantity }}</td>
                                <td>
                                    <input type="number" v-model.number="product.purchaseQuantity" min="1" :max="product.stockQuantity" class="purchase-input"/>
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
import CartButtonComponent from '@/components/CartButtonComponent.vue'

export default {
    name: "ProductView",
    components: {
        CartButtonComponent,
    },
    data() {
        return {
            cartItemCount: 0,
            searchQuery: '',
            products: [
                { id: 1, name: '상품 A', price: '10,000원', stockQuantity: 100, purchaseQuantity: 1 },
                { id: 2, name: '상품 B', price: '20,000원', stockQuantity: 50, purchaseQuantity: 1 },
                // 추가 상품 데이터...
            ],
            // 기타 데이터...
        }
    },
    computed: {
        filteredProducts() {
            return this.products.filter(product => product.name.toLowerCase().includes(this.searchQuery.toLowerCase()));
        }
    },
    methods: {
        addToCart(product) {
            alert(`${product.name} ${product.purchaseQuantity}개가 장바구니에 추가되었습니다.`);
            this.cartItemCount += product.purchaseQuantity; // 장바구니 수량 업데이트
        }
    }
}
</script>

<style scoped>
.table th, .table td {
    text-align: center; /* 모든 셀의 텍스트를 가운데 정렬합니다 */
}

.purchase-input {
    width: 100%; /* 구매 수량 입력 필드 너비를 셀에 맞춥니다 */
    box-sizing: border-box; /* 패딩과 테두리를 너비에 포함시킵니다 */
}

/* 각 컬럼의 너비를 지정할 수 있습니다. 예시는 임의의 값으로, 실제 상황에 맞게 조정하세요 */
.table th:nth-child(1), .table td:nth-child(1) { width: 20%; }
.table th:nth-child(2), .table td:nth-child(2) { width: 15%; }
.table th:nth-child(3), .table td:nth-child(3) { width: 20%; }
.table th:nth-child(4), .table td:nth-child(4) { width: 20%; }
.table th:nth-child(5), .table td:nth-child(5) { width: 25%; }

.btn {
    margin: auto; /* 버튼을 셀 내부에서 가운데 정렬합니다 */
    display: block; /* 버튼을 블록 요소로 만들어 너비 전체를 사용하게 합니다 */
}
.table th, .table td {
    text-align: center; /* 모든 셀의 텍스트를 가운데 정렬합니다 */
    vertical-align: middle; /* 셀의 내용을 수직 중앙에 정렬합니다 */
}
</style>
