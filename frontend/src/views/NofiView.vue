<template>
    <div class="container-fluid px-4">
        
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        공지사항
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">제목</th>
                                    <th scope="col">작성일</th>
                                    <th scope="col">상세보기</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(notice, index) in notices" :key="notice.id">
                                    <th scope="row">{{ index + 1 }}</th>
                                    <td>{{ notice.title }}</td>
                                    <td>{{ notice.date }}</td>
                                    <td>
                                        <button @click="goToNofiDetail(notice.id)" class="btn btn-primary btn-sm">상세보기</button>
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
            notices: [
                { id: 1, title: '공지사항 A', date: '2024-03-10' },
                { id: 2, title: '공지사항 B', date: '2024-03-09' },
                // 추가 공지사항 데이터...
            ],
        };
    },
    methods: {
        goToNofiDetail(id) {
            this.$router.push({ name: 'NofiDetailView', params: { id: id }});
        },
    },
}; 
</script>