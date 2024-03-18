<template>
  <div class="container mt-5">
    <h2 class="mb-3">문의 상세 정보</h2>
    <div class="mb-3">
      <label class="form-label">문의 제목:</label>
      <input
        type="text"
        class="form-control"
        v-model="questionTitle"
        :readonly="!editable"
      />
    </div>
    <div class="mb-3">
      <label class="form-label">문의 내용:</label>
      <textarea
        class="form-control"
        v-model="questionContent"
        rows="3"
        :readonly="!editable"
      ></textarea>
    </div>
    <div class="mb-3">
      <label class="form-label">카테고리:</label>
      <input
        type="text"
        class="form-control"
        v-model="questionCategory"
        :readonly="!editable"
      />
    </div>
    <div class="d-flex justify-content-between">
      <router-link to="/question" class="btn btn-secondary"
        >목록으로 돌아가기</router-link
      >
      <button v-if="!editable" @click="enableEditing" class="btn btn-primary">
        수정
      </button>
      <button v-if="editable" @click="saveChanges" class="btn btn-success">
        저장
      </button>
      <button @click="deleteQuestion" class="btn btn-danger">삭제</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      questionIdx: "",
      questionTitle: "",
      questionContent: "",
      questionCategory: "",
      questionStatus: false,
      editable: false,
    };
  },
  methods: {
    enableEditing() {
      this.editable = true;
    },
    async saveChanges() {
      try {
        const token = sessionStorage.getItem("token");
        console.log("사용할 토큰:", token);

        const response = await axios.patch(
          "http://localhost:8080/question/update",
          {
            idx: this.questionIdx,
            title: this.questionTitle,
            content: this.questionContent,
            category: this.questionCategory,
            status: this.questionStatus,
            // userIdx 제거, 토큰 인증으로 대체
          },
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + token,
            },
          }
        );
        console.log("문의사항 수정 응답:", response.data);
        alert("변경 사항이 저장되었습니다.");
        this.editable = false;
      } catch (error) {
        console.error("문의사항 수정 중 오류:", error);
        console.log("오류 상세:", error.response ? error.response : error);
        alert(
          "문의사항 수정 중 오류가 발생했습니다: " +
            (error.response && error.response.data
              ? error.response.data.message
              : error.message)
        );
      }
    },
    async deleteQuestion() {
      try {
        const token = sessionStorage.getItem("token");
        if (!token) {
          alert("로그인이 필요합니다.");
          return;
        }

        const response = await axios.delete(
          `http://localhost:8080/question/delete?idx=${this.questionIdx}`,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        console.log("문의사항 삭제 응답:", response.data);
        alert("문의사항이 성공적으로 삭제되었습니다.");

        this.questionIdx = "";
        this.questionTitle = "";
        this.questionContent = "";
        this.questionCategory = "";
        this.questionStatus = false;
      } catch (error) {
        console.error("문의사항 삭제 중 오류:", error);
        alert(
          "문의사항 삭제 중 오류가 발생했습니다: " +
            (error.response && error.response.data
              ? error.response.data.message
              : error.message)
        );
      }
    },
  },
  mounted() {
    this.questionIdx = this.$route.query.idx;
    this.questionTitle = this.$route.query.title;
    this.questionContent = this.$route.query.content;
    this.questionCategory = this.$route.query.category;
    this.questionStatus = this.$route.query.status === 'true';
  },
};
</script>
