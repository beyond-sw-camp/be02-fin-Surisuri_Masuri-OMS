<template>
    <div class="container-fluid px-4">
       
        <router-link to="/notice/new" class="btn btn-primary mb-3">공지사항 작성</router-link>
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
                                    <th scope="col">카테고리</th>
                                    <th scope="col">제목</th>
                                    <th scope="col">내용</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(notice, index) in notices" :key="notice.id">
                                    <th scope="row">{{ index + 1 }}</th>
                                    <td>{{ notice.category }}</td>
                                    <td>{{ notice.title }}</td>
                                    <!-- <td>{{ notice.content }}</td> -->
                                    <td>
                                        <router-link :to="{ name: 'NoticeDetail', params: { id: notice.id } }" class="btn btn-primary btn-sm">상세 보기</router-link>
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
    name: 'NoticeList',
    data() {
      return {
        notices: [
          { id: 1, category: '배송 관련', title: '공지사항 제목1', content: ''},
          { id: 2, category: '품질 관련', title: '공지사항 제목2', content: ''},
          { id: 3, category: '입고 날짜 관련', title: '공지사항 제목3', content: ''},
        ],
      };
    },
  };
  </script>
  