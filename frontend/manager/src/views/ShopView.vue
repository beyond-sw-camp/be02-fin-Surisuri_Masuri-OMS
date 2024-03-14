<template>
    <main>
        <div class="container-fluid px-4">
            
            <div class="card mb-4">
                <div class="card-header">
                    <i class="fas fa-table me-1"></i>
                    Shop
                    <input type="text" v-model="searchQuery" placeholder="가맹점 검색..." class="form-control w-25 float-end">
                </div>
                <div class="card-body">
                    <table id="shopTable" class="table">
                        <thead>
                            <tr>
                                <th>가맹점 이름</th>
                                <th>가맹점 주소</th>
                                <th>가맹점 연락처</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(shop, index) in filteredShops" :key="index">
                                <td>{{ shop.name }}</td>
                                <td>{{ shop.addr }}</td>
                                <td>{{ shop.number }}</td>
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
    name: "ShopView",
    components: {
    },
    data() {
        return {
            // cartItemCount: 0,
            searchQuery: '',
            shops: [
                { id: 1, name: '영등포 가맹점', addr: '서울시 영등포구', number: '010-1234-1234' },
                { id: 2, name: '서대문 가맹점', addr: '서울시 서대문구', number: '010-1234-1234' },
                { id: 3, name: '은하 가맹점', addr: '서울시 은하철도구구구', number: '010-1234-1234' },
            ],
            // 기타 데이터...
        }
    },
    computed: {
        filteredShops() {
            return this.shops.filter(shop => shop.name.toLowerCase().includes(this.searchQuery.toLowerCase()));
        }
    },
    methods: {
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
