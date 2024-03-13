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
.stock-input {
     /* 전체 셀 너비의 70%에서 버튼과의 간격을 고려해 조정 */
    display: inline-block; /* 인라인 블록 요소로 설정하여 옆에 다른 요소(버튼)를 둘 수 있게 함 */
    margin-right: 10px; /* 입력 필드와 버튼 사이의 간격 조정 */
}

.btn {
    width: 15%; /* 버튼의 너비를 셀 너비의 30%로 설정 */
    display: inline-block; /* 인라인 블록 요소로 설정하여 옆에 다른 요소(입력 필드)와 나란히 배치 */
}

/* 테이블 셀의 텍스트를 가운데 정렬하려면 다음 CSS도 추가하세요 */
.table th, .table td {
    text-align: center; /* 모든 셀의 텍스트를 가운데 정렬합니다 */
}
.table th, .table td {
    text-align: center; /* 모든 셀의 텍스트를 가운데 정렬합니다 */
    vertical-align: middle; /* 셀의 내용을 수직 중앙에 정렬합니다 */
}
</style>


