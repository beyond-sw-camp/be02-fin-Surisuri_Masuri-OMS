<template>
    <div class="container-fluid px-4">
      <router-link to="/inquiry/new" class="btn btn-primary mb-3"
        >새 문의사항 작성</router-link
      >
      <div class="row">
        <div class="col">
          <div class="card">
            <div class="card-header">문의사항</div>
            <div class="card-body">
              <table class="table">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(question, index) in questions" :key="question.idx">
                    <th scope="row">{{ index + 1 }}</th>
                    <td>{{ question.title }}</td>
                    <td>{{ question.content }}</td>
                    <td>{{ question.category }}</td>
                    <td>
                      <router-link
                        :to="{
                          name: 'InquiryDetail',
                          query: {
                            idx: question.idx,
                            title: question.title,
                            content: question.content,
                            category: question.category,
                            status: question.status
                          }
                        }"
                        class="btn btn-primary btn-sm"
                      >상세 보기</router-link>
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
  
  export default {
    data() {
      return {
        questions: [], // 문의사항 목록을 저장할 배열
        pagination: {
          // 페이지네이션 정보를 저장할 객체
          page: 1, // 기본 페이지 번호
          size: 10, // 기본 페이지당 항목 수, 예시로 10으로 설정
        },
      };
    },
    created() {
      this.fetchQuestions(); // 컴포넌트 생성 시 문의사항 목록을 불러오도록 설정
    },
    methods: {
      async fetchQuestions() {
    try {
      const response = await axios.get(
        "http://localhost:8080/question/list",
        {
          headers: {
            Authorization: "Bearer " + sessionStorage.getItem("token"),
          },
          params: this.pagination, // 페이지네이션 정보를 params로 전달
        }
      );
      // 응답 데이터 전체를 콘솔에 출력
      console.log("응답 데이터:", response.data);
  
      // 응답 데이터 구조에 맞게 questions 배열에 데이터 저장
      this.questions = response.data.result; // 수정: 응답에서 받은 데이터를 questions 배열에 저장
    } catch (error) {
      console.error(
        "문의사항 목록을 불러오는 중 오류가 발생했습니다.",
        error
      );
    }
  },
  
    },
  };
  </script>
  