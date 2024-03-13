<template>
  <div class="container-fluid px-4">
    <div class="container mt-5">
      
      <div class="mb-3">
        <label class="form-label">제목</label>
        <input type="text" class="form-control" v-model="inquiry.title" :readonly="!editable">
      </div>
      <div class="mb-3">
        <label class="form-label">작성일</label>
        <input type="text" class="form-control" v-model="inquiry.date" readonly>
      </div>
      <div class="mb-3">
        <label class="form-label">내용</label>
        <textarea class="form-control" v-model="inquiry.content" rows="5" :readonly="!editable"></textarea>
      </div>
      <div class="mb-3">
        <label class="form-label">답변 내용</label>
        <textarea class="form-control" v-model="inquiry.response" rows="3" :readonly="!editable"></textarea>
      </div>
      <div class="d-flex justify-content-between">
        <router-link to="/inquiry" class="btn btn-secondary">목록으로</router-link>
        <button v-if="!editable" @click="enableEditing" class="btn btn-primary">수정</button>
        <button v-if="editable" @click="updateInquiry" class="btn btn-success">저장</button>
        <button @click="deleteInquiry" class="btn btn-danger">삭제</button>
      </div>
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
        response: '', // 답변 내용을 저장할 속성 추가
      },
      editable: false,
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
      response: '문의사항에 대한 답변입니다.' // 답변 예시 데이터
    };
  },
  methods: {
    enableEditing() {
      this.editable = true;
    },
    updateInquiry() {
      // TODO: 문의사항 수정 로직 구현
      alert('문의사항이 업데이트되었습니다.');
      this.editable = false;
    },
    deleteInquiry() {
      // TODO: 문의사항 삭제 로직 구현
      alert('문의사항이 삭제되었습니다.');
      this.$router.push('/inquiry');
    },
  },
};
</script>

  