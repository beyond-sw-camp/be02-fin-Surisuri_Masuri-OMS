<template>
  <div class="container-fluid px-4">
    <div class="row">
      <div class="col">
        <div class="card">
          <div class="card-header">문의사항</div>
          <div class="card-body">
            <table class="table">
              <thead>
                <tr>
                  <th scope="col" class="text-center align-middle">번호</th>
                  <th scope="col" class="text-center align-middle">제목</th>
                  <th scope="col" class="text-center align-middle">카테고리</th>
                  <th scope="col" class="text-center align-middle">답변하기</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(question, index) in questions" :key="question.questionIdx">
                  <th scope="row" class="text-center align-middle">{{ index + 1 }}</th>
                  <td class="text-center align-middle">{{ question.title }}</td>
                  <td class="text-center align-middle">{{ question.category }}</td>
                  <td class="text-center align-middle">
                    <router-link
                      :to="{
                        name: 'QuestionDetail',
                        query: {
                          idx: question.questionIdx,
                          title: question.title,
                          content: question.content,
                          category: question.category,
                          status: question.status,
                        },
                      }"
                      class="btn btn-primary btn-sm"
                      >상세 보기</router-link
                    >
                  </td>
                </tr>
              </tbody>
            </table>
            <nav aria-label="Page navigation" class="mt-4">
              <ul class="pagination justify-content-end">
                <li
                  class="page-item"
                  :class="{ disabled: pagination.page === 1 }"
                >
                  <a
                    class="page-link"
                    href="#"
                    aria-label="Previous"
                    @click.prevent="navigatePage(pagination.page - 1)"
                  >
                    <span aria-hidden="true">&laquo;</span>
                  </a>
                </li>
                <li
                  class="page-item"
                  v-for="num in visiblePages"
                  :class="{ active: num === pagination.page }"
                  :key="num"
                >
                  <a
                    class="page-link"
                    href="#"
                    @click.prevent="navigatePage(num)"
                    >{{ num }}</a
                  >
                </li>
                <li class="page-item">
                  <a
                    class="page-link"
                    href="#"
                    aria-label="Next"
                    @click.prevent="navigatePage(pagination.page + 1)"
                  >
                    <span aria-hidden="true">&raquo;</span>
                  </a>
                </li>
              </ul>
            </nav>
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
        size: 10, 
        pageCount: 5, // 한 번에 보여질 최대 페이지 번호 수
        totalPage: 20,// 기본 페이지당 항목 수, 예시로 10으로 설정
      },
    };
  },
  created() {
    this.fetchQuestions();
    this.updateVisiblePages();
  },
  methods: {
    async fetchQuestions() {
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.get("http://121.140.125.34:11114/api/question/list", {
          headers: {
            AccessToken:  accessToken, // 'Authorization' 대신 'AccessToken' 사용
          },
          params: this.pagination,
          // 페이지네이션 정보를 params로 전달
        });
        // 응답 데이터 전체를 콘솔에 출력
        console.log("응답 데이터:", response.data);

        // 응답 데이터 구조에 맞게 questions 배열에 데이터 저장
        this.questions = response.data.result; // 수정: 응답에서 받은 데이터를 questions 배열에 저장
      } catch (error) {
        console.error("문의사항 목록을 불러오는 중 오류가 발생했습니다.", error);
      }
    },
    updateVisiblePages() {
      this.visiblePages = [];
      let startPage =
        Math.floor((this.pagination.page - 1) / this.pagination.pageCount) *
          this.pagination.pageCount +
        1;
      for (
        let i = startPage;
        i < startPage + this.pagination.pageCount &&
        i <= this.pagination.totalPage;
        i++
      ) {
        this.visiblePages.push(i);
      }
    },

    navigatePage(page) {
      this.pagination.page = page;
      this.fetchQuestions();
      this.updateVisiblePages();
    },
  },
};
</script>
