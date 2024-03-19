<template>
  <div class="create-question container py-5">
    <form @submit.prevent="submitQuestion" class="needs-validation" novalidate>
      <div class="mb-3">
        <label for="title" class="form-label">제목:</label>
        <input id="title" v-model="question.title" type="text" class="form-control" required>
        <div class="invalid-feedback">
          제목을 입력해주세요.
        </div>
      </div>
      <div class="mb-3">
        <label for="content" class="form-label">내용:</label>
        <textarea id="content" v-model="question.content" class="form-control" rows="3" required></textarea>
        <div class="invalid-feedback">
          내용을 입력해주세요.
        </div>
      </div>
      <button type="submit" class="btn btn-primary">문의사항 제출</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      question: {
        title: '',
        content: '',
        category: '기본 카테고리',
        status: false,
      },
    };
  },
  methods: {
    async submitQuestion() {
      try {
        const token = sessionStorage.getItem('token');
        if (!token) {
          console.error('토큰이 없습니다.');
          return;
        }

        await axios.post('http://localhost:8080/question/create', this.question, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          }
        });

        alert('문의사항이 성공적으로 제출되었습니다.');
        // 문의사항 제출 후 /inquiry 페이지로 리다이렉트
        this.$router.push('/question');
      } catch (error) {
        console.error(`문의사항 제출 실패: ${error}`);
        alert('문의사항 제출에 실패했습니다. 다시 시도해주세요.');
      }
    },
  },
};
</script>