<template>
    <main>
        <div class="container-fluid px-4">
            
            <div class="card mb-4">
                <div class="card-header">
                    <i class="fas fa-table me-1"></i>
                    Inventory
                </div>
                <div class="card-body">
                    <table id="stockTable" class="table">
                        <thead>
                            <tr>
                                <th>상품이름</th>
                                <th>현재 재고수량</th>
                                <th>재고수량 수정</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(product, index) in products" :key="index">
                                <td>{{ product.name }}</td>
                                <td>{{ product.stockQuantity }}</td>
                                <td>
                                    <input type="number" v-model.number="product.editStockQuantity" min="0" class="stock-input"/>
                                    <button @click="updateStockQuantity(index)" class="btn btn-primary">수정</button>
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
    name: "StockEditView",
    data() {
        return {
            products: [
                { id: 1, name: '상품 A', stockQuantity: 100, editStockQuantity: 100 },
                { id: 2, name: '상품 B', stockQuantity: 50, editStockQuantity: 50 },
                // 추가 상품 데이터...
            ],
        }
    },
    methods: {
        updateStockQuantity(index) {
            const product = this.products[index];
            product.stockQuantity = product.editStockQuantity;
            alert(`${product.name}의 재고 수량이 ${product.stockQuantity}로 수정되었습니다.`);
        },
    }
}
</script>

<style scoped>
.btn {
    margin-left: 10px; /* 버튼 간격 조정 */
}
</style>
