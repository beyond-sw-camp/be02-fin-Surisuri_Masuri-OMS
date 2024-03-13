<template>
    <div class="container-fluid px-4">
      <div class="container mt-5">
        <h1 class="mt-4">문의사항 생성</h1>
        <form @submit.prevent="submitInquiry">
          <div class="mb-3">
            <label for="inquiryTitle" class="form-label">제목</label>
            <input type="text" class="form-control" id="inquiryTitle" v-model="inquiry.title" required>
          </div>
          <div class="mb-3">
            <label for="inquiryContent" class="form-label">내용</label>
            <textarea class="form-control" id="inquiryContent" v-model="inquiry.content" rows="5" required></textarea>
          </div>
          <button type="submit" class="btn btn-primary">문의사항 제출</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'CreateInquiry',
    data() {
      return {
        inquiry: {
          title: '',
          content: '',
        },
      };
    },
    methods: {
      submitInquiry() {
        // TODO: 문의사항 제출 로직 구현
        // 여기서는 예시로 console.log를 사용했지만, 실제로는 서버로 데이터를 전송하는 로직을 구현해야 합니다.
        console.log('Submitted inquiry:', this.inquiry);
        alert('문의사항이 제출되었습니다.');
        this.$router.push('/inquiry'); // 제출 후 문의사항 목록 페이지로 이동
      },
    },
  };
  </script>
  
  <style scoped>
  </style>
  