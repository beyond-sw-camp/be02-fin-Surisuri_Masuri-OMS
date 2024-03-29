<template>
  <div class="container-fluid px-4">
    <router-link to="/question/new" class="btn btn-primary mb-3">새 문의사항 작성</router-link>
    <div class="row">
      <div class="col">
        <div class="card">
          <div class="card-header">문의사항</div>
          <div class="card-body">
            <table class="table">
              <thead>
                <tr>
                  <th scope="col">번호</th>
                  <th scope="col">제목</th>
                  <th scope="col" class="text-center">카테고리</th>
                  <th scope="col" class="text-center">상세 보기</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(question, index) in questions" :key="question.idx">
                  <th scope="row">{{ index + 1 }}</th>
                  <td>{{ question.title }}</td>
                  <!-- 카테고리 가운데 정렬 -->
                  <td class="text-center">{{ question.category }}</td>
                  <!-- 상세 보기 버튼 가운데 정렬 -->
                  <td class="text-center">
                    <router-link
                      :to="{
                        name: 'QuestionDetail',
                        query: {
                          idx: question.questionIdx,
                          title: question.title,
                          content: question.content,
                          category: question.category,
                          status: question.status,
                          answerContent: question.answerContent,
                        },
                      }"
                      class="btn btn-primary btn-sm"
                      >상세 보기</router-link
                    >
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { getErrorMessage } from "../utils/error.js"; // 에러 메시지 처리 함수 임포트

export default {
  data() {
    return {
      questions: [],
      pagination: {
        page: 1,
        size: 10,
      },
    };
  },
  created() {
    this.fetchQuestions();
  },
  methods: {
    async fetchQuestions() {
      try {
        const response = await axios.get("http://192.168.0.45/question/list", {
          headers: {
            Authorization: "Bearer " + sessionStorage.getItem("token"),
          },
          params: this.pagination,
        });
        console.log("응답 데이터:", response.data);
        this.questions = response.data.result;
      } catch (error) {
        console.error("문의사항 목록을 불러오는 중 오류가 발생했습니다.", error);
        const message = getErrorMessage(error.response && error.response.data ? error.response.data.errorCode : null);
        alert(message);
      }
    },
  },
};
</script>
