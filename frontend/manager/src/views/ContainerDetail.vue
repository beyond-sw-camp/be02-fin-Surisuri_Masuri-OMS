<template>
    <div class="container-fluid px-4">
        
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        상품 목록
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">번호</th>
                                    <th scope="col">상품 이름</th>
                                    <th scope="col">재고</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(containers, index) in containerss" :key="containers.id">
                                    <th scope="row">{{ index + 1 }}</th>
                                    <td>{{ containers.productName }}</td>
                                    <td>{{ containers.productQuantity }}</td>
                                    <td>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <a @click="goBack" class="btn btn-primary btn-sm">목록으로 돌아가기</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
export default {
    data() {
        return {
            containerss: [
                { id: 1, productName: '상품1', productQuantity: '100' },
                { id: 2, productName: '상품2', productQuantity: '200' },
            ],
        };
    },
    methods: {
        goBack() {
            this.$router.go(-1);
        }
    }
}; 
</script>