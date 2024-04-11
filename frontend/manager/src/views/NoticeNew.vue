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
          <label for="noticeContent" class="form-label">내용</label>
          <textarea class="form-control" id="noticeContent" v-model="notice.content" rows="5" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">작성</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import swal from 'sweetalert';
export default {
  name: 'CreateNotice',
  data() {
    return {
      notice: {
        title: '',
        content: '',
        category: '기본 카테고리', // 카테고리 고정값
        status: true, // 상태 고정값
      },
    };
  },
  methods: {
    async submitNotice() {
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.post('http://121.140.125.34:11114/api/notice/create', this.notice, {
          headers: {
            'Content-Type': 'application/json',
             AccessToken: accessToken,

          },
        });
        console.log('Submitted notice:', response.data);
        swal('공지사항이 성공적으로 생성되었습니다.');
        this.$router.push('/notice'); // 제출 후 공지사항 목록 페이지로 이동
      } catch (error) {
        console.error('공지사항 생성 중 오류 발생:', error);
        swal('공지사항 생성 중 오류가 발생했습니다.');
      }
    },
  },
};
</script>
