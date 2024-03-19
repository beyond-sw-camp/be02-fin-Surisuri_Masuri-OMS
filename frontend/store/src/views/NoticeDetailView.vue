<template>
    <div class="container-fluid px-4">
    
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        공지사항 상세
                    </div>
                    <div class="card-body">
                        <p>제목: {{ this.$route.query.title }}</p>
                        <p>내용: {{ this.$route.query.content }}</p> 
                        <a @click="goBack" class="btn btn-primary btn-sm">목록으로 돌아가기</a>
                    </div>
                </div>
            </div>
        </div>
    
    </div>
</template>
<script>
export default {
  methods: {
    goBack() {
      this.$router.push('/notice'); // 공지사항 목록 페이지로 이동
    },
  },
};
</script>

