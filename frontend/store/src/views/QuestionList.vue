<template>
  <div class="container-fluid px-4">
    <router-link to="/question/new" class="btn btn-primary mb-3"
      >새 문의사항 작성</router-link
    >
    <div class="row">
      <div class="col">
        <div class="card">
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
import { getErrorMessage } from "../utils/error.js"; // 에러 메시지 처리 함수 임포트
import swal from 'sweetalert';
export default {
  data() {
    return {
      questions: [],
      pagination: {
        page: 1,
        size: 10,
        pageCount: 5, // 한 번에 보여질 최대 페이지 번호 수
        totalPage: 20,
      },
      visiblePages: [],
    };
  },
  created() {
    this.fetchQuestions();
    this.fetchQuestions();
    this.updateVisiblePages();
  },
  methods: {
    async fetchQuestions() {
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.get(
          "http://121.140.125.34:11113/api/question/list",
          {
            headers: {
              AccessToken: accessToken,
            },
            params: this.pagination,
          }
        );
        console.log("응답 데이터:", response.data);
        this.questions = response.data.result;
      } catch (error) {
        console.error(
          "문의사항 목록을 불러오는 중 오류가 발생했습니다.",
          error
        );
        const message = getErrorMessage(
          error.response && error.response.data
            ? error.response.data.errorCode
            : null
        );
        swal(message);
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
<style scoped>
</style>


