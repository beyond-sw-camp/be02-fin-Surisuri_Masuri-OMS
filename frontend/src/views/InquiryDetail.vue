<template>
    <div class="container-fluid px-4">
    <div class="container mt-5">
    <h1 class="mt-4">문의사항 상세</h1>
      <div class="mb-3">
        <label class="form-label">제목</label>
        <input type="text" class="form-control" v-model="inquiry.title" readonly>
      </div>
      <div class="mb-3">
        <label class="form-label">작성일</label>
        <input type="text" class="form-control" v-model="inquiry.date" readonly>
      </div>
      <div class="mb-3">
        <label class="form-label">내용</label>
        <textarea class="form-control" v-model="inquiry.content" rows="5" readonly></textarea>
      </div>
      <router-link to="/inquiry" class="btn btn-secondary">목록으로</router-link>
    </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'InquiryDetail',
    data() {
      return {
        inquiry: {
          id: this.$route.params.id,
          title: '',
          date: '',
          content: '',
        },
      };
    },
    mounted() {
      // TODO: 문의사항 ID에 해당하는 상세 정보를 불러오는 로직 구현
      // 예시 데이터
      this.inquiry = {
        id: this.$route.params.id,
        title: '문의사항 예제 1',
        date: '2024-03-15',
        content: '문의사항 상세 내용입니다.',
      };
    },
  };
  </script>
  