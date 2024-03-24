<template>
  <div class="container mt-5">
    <h2 class="mb-3">문의 상세 정보</h2>
    <div class="mb-3">
      <label class="form-label">문의 제목:</label>
      <input type="text" class="form-control" v-model="questionTitle" readonly />
    </div>
    <div class="mb-3">
      <label class="form-label">문의 내용:</label>
      <textarea class="form-control" v-model="questionContent" rows="3" readonly></textarea>
    </div>
    <div class="mb-3">
      <label class="form-label">카테고리:</label>
      <input type="text" class="form-control" v-model="questionCategory" readonly />
    </div>

    <input type="text" v-model="answerContent" placeholder="답변을 입력하세요" class="form-control mb-3" />
    <button @click="submitAnswer" class="btn btn-primary">답변 제출</button>

    <router-link to="/question" class="btn btn-secondary mt-3">목록으로 돌아가기</router-link>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      questionIdx: "", // 문의사항의 ID
      questionTitle: "", // 문의사항 제목
      questionContent: "", // 문의사항 내용
      questionCategory: "", // 문의사항 카테고리
      questionStatus: true, // 문의사항 상태
      answerContent: "", // 관리자가 입력하는 답변 내용
    };
  },
  methods: {
    async submitAnswer() {
      try {
        const token = sessionStorage.getItem("token");
        if (!token) {
          alert("로그인이 필요합니다.");
          return;
        }
        const answerReq = {
          questionIdx: this.questionIdx,
          answerContent: this.answerContent,
        };

        const response = await axios.post("http://localhost:8080/question/answer", answerReq, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        });

        console.log("답변 제출 응답:", response.data);

        // localStorage에 답변 저장
        localStorage.setItem(`answerContent_${this.questionIdx}`, this.answerContent);

        alert("답변이 성공적으로 제출되었습니다.");
        // 답변 제출 후에도 내용을 초기화하지 않음
        this.$router.push("/question");
      } catch (error) {
        console.error("답변 제출 중 오류:", error);
        alert(
          "답변 제출 중 오류가 발생했습니다: " +
            (error.response && error.response.data ? error.response.data.message : error.message)
        );
      }
    },
  },
  mounted() {
    const query = this.$route.query;
    this.questionIdx = query.idx;
    this.questionTitle = query.title;
    this.questionContent = query.content;
    this.questionCategory = query.category;
    this.questionStatus = query.status === "true";

    // localStorage에서 답변 내용 로드
    const savedAnswerContent = localStorage.getItem(`answerContent_${this.questionIdx}`);
    if (savedAnswerContent) {
      this.answerContent = savedAnswerContent;
    }
  },
};
</script>
