<template>
    <div class="container-fluid px-4">
      <div class="container mt-5">
        <h1 class="mt-4">공지사항 생성</h1>
        <form @submit.prevent="submitNotice">
          <div class="mb-3">
            <label for="noticeTitle" class="form-label">제목</label>
            <input type="text" class="form-control" id="noticeTitle" v-model="notice.title" required>
          </div>
          <div class="mb-3">
            <label for="noticeCategory" class="form-label">카테고리</label>
            <input type="text" class="form-control" id="noticeCategory" v-model="notice.category" required>
          </div>
          <div class="mb-3">
            <label for="noticeContent" class="form-label">내용</label>
            <textarea class="form-control" id="noticeContent" v-model="notice.content" rows="5" required></textarea>
          </div>
          <button type="submit" class="btn btn-primary">작성</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'CreateNotice',
    data() {
      return {
        notice: {
          title: '',
          category: '',
          content: '',
        },
      };
    },
    methods: {
      submitNotice() {
        // TODO: 문의사항 제출 로직 구현
        // 여기서는 예시로 console.log를 사용했지만, 실제로는 서버로 데이터를 전송하는 로직을 구현해야 합니다.
        console.log('Submitted notice:', this.notice);
        alert('문의사항이 제출되었습니다.');
        this.$router.push('/notice'); // 제출 후 문의사항 목록 페이지로 이동
      },
    },
  };
  </script>
  
  <style scoped>
  </style>
  