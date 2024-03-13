<template>
    <div class="container-fluid px-4">
    
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        공지사항 상세
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">{{ notice.title }}</h5>
                        <h6 class="card-subtitle mb-2 text-muted">{{ notice.date }}</h6>
                        <p class="card-text">{{ notice.content }}</p>
                        <a @click="goBack" class="card-link">목록으로 돌아가기</a>
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
            notice: {},
        };
    },
    mounted() {
        const id = this.$route.params.id;
        // 여기서 id에 해당하는 공지사항 데이터를 불러와서 notice 객체를 업데이트합니다.
        this.notice = { id: id, title: '공지사항 A', date: '2024-03-10', content: '공지사항 A의 내용입니다.' };
    },
    methods: {
        goBack() {
            this.$router.go(-1);
        }
    }
};
</script>