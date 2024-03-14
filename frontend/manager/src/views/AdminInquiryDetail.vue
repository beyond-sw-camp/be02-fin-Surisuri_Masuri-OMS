<template>
    <div class="container-fluid px-4">
      <div class="container mt-5">
        <!-- 문의사항 상세 정보 -->
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
  
        <!-- 관리자 답변 섹션 -->
        <div class="mb-3">
          <label class="form-label">답변 내용</label>
          <textarea class="form-control" v-model="inquiry.response" rows="3"></textarea>
        </div>
        <div class="d-flex justify-content-between">
          <router-link to="/inquiries" class="btn btn-secondary">목록으로</router-link>
          <button @click="updateResponse" class="btn btn-success">답변 저장</button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'AdminInquiryDetail',
    data() {
      return {
        inquiry: {
          id: this.$route.params.id,
          title: '',
          date: '',
          content: '',
          response: '', // 관리자 답변을 저장할 속성
        },
      };
    },
    mounted() {
      // TODO: 문의사항 ID에 해당하는 상세 정보를 불러오는 로직 구현
    },
    methods: {
      updateResponse() {
        // TODO: 관리자 답변을 저장하는 로직 구현
        alert('답변이 저장되었습니다.');
      },
    },
  };
  </script>
  