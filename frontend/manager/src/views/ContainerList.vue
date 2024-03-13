<template>
    <div class="container-fluid px-4">
        
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        창고 목록
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">창고 이름</th>
                                    <th scope="col">창고 주소</th>
                                    <th scope="col">담당자</th>
                                    <th scope="col">담당자 번호</th>
                                    <th scope="col">창고 복잡도</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(containers, index) in containerss" :key="containers.id">
                                    <th scope="row">{{ index + 1 }}</th>
                                    <td>{{ containers.containerName }}</td>
                                    <td>{{ containers.containerAddr }}</td>
                                    <td>{{ containers.containerManger }}</td>
                                    <td>{{ containers.managerPhone }}</td>
                                    <td>{{ containers.containerComp }}</td>
                                    <td>
                                        <button @click="goToContainerDetail(containers.id)" class="btn btn-primary btn-sm">상세보기</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
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
                { id: 1, containerName: '창고1', containerAddr: '서울' , containerManger: '김서울' , managerPhone: '010-1234-1234' , containerComp: '1'},
                { id: 2, containerName: '창고2', containerAddr: '경기' , containerManger: '최경기' , managerPhone: '010-1234-1234' , containerComp: '0'},
                { id: 3, containerName: '창고3', containerAddr: '대전' , containerManger: '박대전' , managerPhone: '010-1234-1234' , containerComp: '0'},
                { id: 4, containerName: '창고4', containerAddr: '부산' , containerManger: '송부산' , managerPhone: '010-1234-1234' , containerComp: '1'},
                { id: 5, containerName: '창고5', containerAddr: '제주' , containerManger: '이제주' , managerPhone: '010-1234-1234' , containerComp: '1'},
                
            ],
        };
    },
    methods: {
        goToContainerDetail(id) {
            this.$router.push({ name: 'ContainerDetail', params: { id: id }});
        },
    },
}; 
</script>