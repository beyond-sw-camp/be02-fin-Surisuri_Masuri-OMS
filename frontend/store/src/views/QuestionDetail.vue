<template>
  <div class="container mt-5">
    <!-- 흰색 카드로 감싸기 -->
    <div class="card">
      <div class="card-body">
        
        <div class="mb-3">
          <label class="form-label">문의 제목</label>
          <input type="text" class="form-control" v-model="questionTitle" :readonly="!editable" />
        </div>
        <div class="mb-3">
          <label class="form-label">문의 내용</label>
          <textarea class="form-control" v-model="questionContent" rows="3" :readonly="!editable"></textarea>
        </div>
        <div class="mb-3">
          <label class="form-label">카테고리</label>
          <input type="text" class="form-control" v-model="questionCategory" :readonly="!editable" />
        </div>
        <!-- answerContent가 있을 때만 보이는 섹션 -->
        <div class="mb-3" v-if="answerContent">
          <label class="form-label">답변 내용</label>
          <textarea class="form-control" v-model="answerContent" rows="3" readonly></textarea>
        </div>
        <div class="d-flex justify-content-between">
          <router-link to="/question" class="btn btn-secondary">목록으로 돌아가기</router-link>
          <button v-if="!editable" @click="enableEditing" class="btn btn-primary">수정</button>
          <button v-if="editable" @click="saveChanges" class="btn btn-success">저장</button>
          <button @click="deleteQuestion" class="btn btn-danger">삭제</button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from "axios";
import { getErrorMessage } from "../utils/error.js";
import swal from 'sweetalert';
export default {
  data() {
    return {
      questionIdx: "",
      questionTitle: "",
      questionContent: "",
      questionCategory: "",
      questionStatus: false,
      answerContent: "", // 답변 내용 추가
      editable: false,
    };
  },
  methods: {
    enableEditing() {
      this.editable = true;
    },
    async saveChanges() {
      const accessToken = sessionStorage.getItem("accessToken");
      if (!accessToken) {
        swal("로그인이 필요합니다.");
        return;
      }
      try {
        await axios.patch(
          "http://121.140.125.34:11113/api/question/update",
          {
            idx: this.questionIdx,
            title: this.questionTitle,
            content: this.questionContent,
            category: this.questionCategory,
            status: this.questionStatus,
            // answerContent는 수정 대상에서 제외됩니다.
          },
          {
            headers: {
              "Content-Type": "application/json",
              'AccessToken': accessToken,
            },
          }
        );
        swal("변경 사항이 저장되었습니다.");
        this.editable = false;
        this.$router.push("/question"); // 수정 후 inquiry 목록으로 리다이렉트
      } catch (error) {
        const message = getErrorMessage(error.response && error.response.data ? error.response.data.errorCode : null);
        swal("문의사항 수정 중 오류가 발생했습니다: " + message);
      }
    },
    async deleteQuestion() {
      const accessToken = sessionStorage.getItem("accessToken");
      if (!accessToken) {
        swal("로그인이 필요합니다.");
        return;
      }
      try {
        await axios.delete(`http://121.140.125.34:11113/api/question/delete?idx=${this.questionIdx}`, {
          headers: {
            'AccessToken': accessToken,
          },
        });
        swal("문의사항이 성공적으로 삭제되었습니다.");
        this.$router.push("/question"); // 삭제 후 inquiry 목록으로 리다이렉트
      }catch (error) {
        const message = getErrorMessage(error.response && error.response.data ? error.response.data.errorCode : null);
        swal("문의사항 삭제 중 오류가 발생했습니다: " + message);
      }
    },
  },
  mounted() {
    const { idx, title, content, category, status, answerContent } = this.$route.query;
    this.questionIdx = idx;
    this.questionTitle = title;
    this.questionContent = content;
    this.questionCategory = category;
    this.questionStatus = status === "true";
    this.answerContent = answerContent; // answerContent를 초기화합니다.
  },
};
</script>
