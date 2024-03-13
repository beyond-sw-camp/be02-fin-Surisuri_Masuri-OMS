<template>
  <div class="container-fluid px-4">
    <div class="container mt-5">
      
      <div class="mb-3">
        <label class="form-label">제목</label>
        <input type="text" class="form-control" v-model="notice.title" :readonly="!editable">
      </div>
      <div class="mb-3">
        <label class="form-label">카테고리</label>
        <input type="text" class="form-control" v-model="notice.category" readonly>
      </div>
      <div class="mb-3">
        <label class="form-label">작성일</label>
        <input type="text" class="form-control" v-model="notice.date" readonly>
      </div>
      <div class="mb-3">
        <label class="form-label">내용</label>
        <textarea class="form-control" v-model="notice.content" rows="5" :readonly="!editable"></textarea>
      </div>
      <div class="d-flex justify-content-between">
        <router-link to="/notice" class="btn btn-secondary">목록으로</router-link>
        <button v-if="!editable" @click="enableEditing" class="btn btn-primary">수정</button>
        <button v-if="editable" @click="updateNotice" class="btn btn-success">저장</button>
        <button @click="deleteNotice" class="btn btn-danger">삭제</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NoticeDetail',
  data() {
    return {
      notice: {
        id: this.$route.params.id,
        title: '',
        category: '',
        date: '',
        content: '',
      },
      editable: false,
    };
  },
  mounted() {
    // TODO: 공지사항 ID에 해당하는 상세 정보를 불러오는 로직 구현
    // 예시 데이터
    this.notice = {
      id: this.$route.params.id,
      title: '공지사항 제목1',
      category: '배송 관련',
      date: '2024-03-15',
      content: '공지사항 상세 내용입니다.',
    };
  },
  methods: {
    enableEditing() {
      this.editable = true;
    },
    updateNotice() {
      // TODO: 공지사항 수정 로직 구현
      alert('공지사항이 업데이트되었습니다.');
      this.editable = false;
    },
    deleteNotice() {
      // TODO: 공지사항 삭제 로직 구현
      alert('공지사항이 삭제되었습니다.');
      this.$router.push('/notice');
    },
  },
};
</script>

  