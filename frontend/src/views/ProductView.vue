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
.btn {
    margin-left: 10px; /* 버튼 간격 조정 */
}
</style>
