<template>
    <div class="container-fluid px-4">
      <h2 class="mt-4 mb-4">문의사항 목록</h2>
      <div class="row">
        <div class="col">
          <div class="card">
            <div class="card-header">모든 문의사항</div>
            <div class="card-body">
              <table class="table">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">제목</th>
                    <th scope="col">작성일</th>
                    <th scope="col">상태</th>
                    <th scope="col">상세보기</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(inquiry, index) in inquiries" :key="inquiry.id">
                    <th scope="row">{{ index + 1 }}</th>
                    <td>{{ inquiry.title }}</td>
                    <td>{{ inquiry.date }}</td>
                    <td>{{ inquiry.status }}</td>
                    <td>
                      <router-link :to="{ name: 'AdminInquiryDetail', params: { id: inquiry.id } }" class="btn btn-primary btn-sm">상세 보기</router-link>
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
  export default {
    name: 'AdminInquiryList',
    data() {
      return {
        inquiries: [
          { id: 1, title: '문의사항 예제 1', date: '2024-03-15', status: '답변 완료' },
          { id: 2, title: '문의사항 예제 2', date: '2024-03-16', status: '대기 중' },
          // 추가 문의사항 데이터...
        ],
      };
    },
    // 문의사항 데이터를 서버로부터 불러오는 로직을 여기에 구현합니다.
    mounted() {
      this.fetchInquiries();
    },
    methods: {
      fetchInquiries() {
        // 서버로부터 문의사항 목록을 불러오는 API 요청 구현
        // 예시: axios.get('/api/inquiries').then((response) => { this.inquiries = response.data; });
      }
    },
  };
  </script>
  