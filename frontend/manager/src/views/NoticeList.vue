<template>
  <div class="container-fluid px-4">
    <div class="row">
      <div class="col">
        <div class="card">
          <div class="card-body">
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
            <router-link to="/noticenew" class="btn btn-success float-end"> 공지사항 작성하기 </router-link>
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
    };
  },
  created() {
    this.fetchNotices();
  },
  methods: {
    async fetchNotices() {
      try {
        const response = await axios.get("http://121.140.125.34:11114/api/notice/list", {
          params: {
            page: 1,
            size: 10,
          },
        });
        this.notices = response.data.result;

        // 공지사항 목록을 성공적으로 불러온 후 각 공지사항의 idx를 콘솔에 출력
        this.notices.forEach((notice) => {
          console.log(`공지사항 idx: ${notice.noticeIdx}`);
        });
      } catch (error) {
        console.error("공지사항 목록을 불러오는 중 오류가 발생했습니다.", error);
      }
    },
  },
};
</script>
