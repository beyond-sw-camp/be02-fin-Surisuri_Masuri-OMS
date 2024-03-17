<template>
    <div class="container-fluid px-4">
        
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        공지사항
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">제목</th>
                                    <th scope="col">내용</th>
                                    <th scope="col">상세보기</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(notice, index) in notices" :key="notice.idx">
                                    <th scope="row">{{ index + 1 }}</th>
                                    <td>{{ notice.title }}</td>
                                    <td>{{ notice.content }}</td>
                                    <td>
                                  </td>    
                                  <router-link :to="{
                                    name: 'NoticeDetail',
                                    query: {
                                        idx: notice.idx,
                                        title: notice.title,
                                        content: notice.content
                                    }
                                }" class="btn btn-primary btn-sm">상세 보기</router-link>
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
import axios from 'axios';

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
        const response = await axios.get('http://localhost:8080/notice/list', {
          params: {
            page: 1,
            size: 1, // size 값을 조정했습니다. 예시로 1이 아니라 10으로 설정
          }
        });
        // 응답 데이터 구조에 맞게 notices 배열에 데이터 저장
        this.notices = response.data.result; // 수정: 'data.data'에서 'data.result'로 변경
      } catch (error) {
        console.error('공지사항 목록을 불러오는 중 오류가 발생했습니다.', error);
      }
    },
    goToNofiDetail(idx) {
      this.$router.push({ name: 'NofiDetailView', params: { idx: idx }});
    },
  },
};
</script>

  