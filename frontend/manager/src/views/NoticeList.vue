<template>
  <div class="container-fluid px-4">
    <div class="row">
      <div class="col">
        <div class="card">
          <div class="card-body">
            <router-link to="/noticenew" class="btn btn-success"> 공지사항 작성하기 </router-link>
            <table class="table">
              <thead>
                <tr>
                  <th scope="col">번호</th>
                  <th scope="col">제목</th>
                  <th scope="col">내용</th>
                  <th scope="col">상세보기</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(notice, index) in notices" :key="notice.noticeIdx">
                  <th scope="row">{{ index + 1 }}</th>
                  <td>{{ notice.title }}</td>
                  <td>{{ notice.content }}</td>
                  <td>
                    <router-link
                      :to="{
                        name: 'NoticeDetail',
                        query: {
                          idx: notice.noticeIdx,
                          title: notice.title,
                          content: notice.content,
                          category: notice.category,
                        },
                      }"
                      class="btn btn-primary btn-sm"
                    >
                      상세 보기
                    </router-link>
                  </td>
                </tr>
              </tbody>
            </table>
            <nav aria-label="Page navigation">
              <ul class="pagination justify-content-end">
                <li class="page-item">
                  <a class="page-link" href="#" aria-label="Previous" @click.prevent="prevRange">
                    <span aria-hidden="true">&laquo;</span>
                  </a>
                </li>
                <li class="page-item" v-for="page in pageRange" :key="page" :class="{ active: page === currentPage }">
                  <a class="page-link" href="#" @click.prevent="fetchNotices(page)">{{ page }}</a>
                </li>
                <li class="page-item">
                  <a class="page-link" href="#" aria-label="Next" @click.prevent="nextRange">
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
      notices: [],
      currentPage: 1,
      totalPages: 20, // 실제 서버로부터 받아온 총 페이지 수입니다.
      pageRangeSize: 5, // 한 번에 보여줄 페이지 수입니다.
    };
  },
  computed: {
    pageRange() {
      const startPage = Math.floor((this.currentPage - 1) / this.pageRangeSize) * this.pageRangeSize + 1;
      const endPage = Math.min(startPage + this.pageRangeSize - 1, this.totalPages);
      return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
    },
  },
  created() {
    this.fetchNotices(this.currentPage);
  },
  methods: {
    async fetchNotices(page) {
      this.currentPage = page;
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.get("http://121.140.125.34:11114/api/notice/list", {
          params: {
            page: this.currentPage,
            size: 5,
          },
          headers: {
            AccessToken: accessToken,
          },
        });
        this.notices = response.data.result;
      } catch (error) {
        console.error("공지사항 목록을 불러오는 중 오류가 발생했습니다.", error);
      }
    },
    nextRange() {
      const maxPage =
        Math.ceil(this.currentPage / this.pageRangeSize) * this.pageRangeSize;
      if (maxPage < this.totalPages) {
        this.fetchNotices(maxPage + 1);
      }
    },
    prevRange() {
      const startPage =
        Math.floor((this.currentPage - 1) / this.pageRangeSize) *
        this.pageRangeSize;
      if (startPage >= this.pageRangeSize) {
        this.fetchNotices(startPage - this.pageRangeSize + 1);
      }
    },
  },
};
</script>
