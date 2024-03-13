<template>
    <div class="container-fluid px-4">
       
        <router-link to="/inquiry/new" class="btn btn-primary mb-3">새 문의사항 작성</router-link>
        <div class="row">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        문의사항
                    </div>
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
                                        <router-link :to="{ name: 'InquiryDetail', params: { id: inquiry.id } }" class="btn btn-primary btn-sm">상세 보기</router-link>
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
    name: 'InquiryList',
    data() {
      return {
        inquiries: [
          { id: 1, title: '문의사항 예제 1', date: '2024-03-15', status: '답변 완료' },
          { id: 2, title: '문의사항 예제 2', date: '2024-03-16', status: '대기 중' },
        ],
      };
    },
  };
  </script>
  