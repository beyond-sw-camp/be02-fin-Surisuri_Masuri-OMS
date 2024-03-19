<template>
    <div id="layoutAuthentication_content">
      <main>
        <div class="container">
          <div class="row justify-content-center">
            <div class="col-lg-5">
              <div class="card shadow-lg border-0 rounded-lg mt-5">
                <div class="card-header">
                  <h3 class="text-center font-weight-light my-4">비밀번호 찾기</h3>
                </div>
                <div class="card-body">
                  <div class="small mb-3 text-muted">이름과 이메일을 입력하시면 비밀번호를 재설정 링크를 보내드립니다.</div>
                  <form @submit.prevent="resetPassword">
                    <div class="form-floating mb-3">
                      <input v-model="userName" class="form-control" id="inputName" type="name" placeholder="" />
                      <label for="inputName">이름</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="userEmail" class="form-control" id="inputEmail" type="email" placeholder="" />
                      <label for="inputEmail">Email</label>
                    </div>
                    <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                      <router-link class="small" to="/">로그인</router-link>
                      <button type="submit" class="btn btn-primary">비밀번호 재설정</button>
                    </div>
                  </form>
                </div>
                <div class="card-footer text-center py-3">
                  <div class="small"><router-link to="/register">회원가입</router-link></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        userName: '',
        userEmail: '',
      };
    },
    methods: {
      async resetPassword() {
        if (!this.userName.trim() || !this.userEmail.trim()) {
          alert('이름과 이메일을 모두 입력해주세요.');
          return;
        }
  
        try {
          const response = await axios.post('http://localhost:8080/user/findPassword', {
            name: this.userName, // API 요청에 맞게 필드 이름이 수정됐는지 확인하세요.
            email: this.userEmail, // API 요청에 맞게 필드 이름이 수정됐는지 확인하세요.
          });
  
          if (response.data.success) {
            // 성공적으로 처리되었을 때의 메시지
            alert('비밀번호 재설정 링크가 이메일로 전송되었습니다. 메일을 확인해주세요.');
          } else {
            // 서버가 처리는 했으나 성공적으로 처리되지 않은 경우
            alert('비밀번호 재설정 요청을 처리할 수 없습니다. 입력 정보를 확인해주세요.');
          }
        } catch (error) {
          let errorMessage = '비밀번호 재설정 요청 중 오류가 발생했습니다.';
          if (error.response && error.response.data && error.response.data.message) {
            // 서버로부터 받은 오류 메시지가 있는 경우 사용
            errorMessage += `\n${error.response.data.message}`;
          }
          alert(errorMessage);
        }
      },
    },
  };
  </script>
  