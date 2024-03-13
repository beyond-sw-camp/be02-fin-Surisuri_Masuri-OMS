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
                  <div class="small mb-3 text-muted">이메일 주소를 입력하시면 비밀번호를 재설정할 수 있는 링크를 보내드립니다..</div>
                  <form @submit.prevent="resetPassword">
                    <div class="form-floating mb-3">
                      <input v-model="email" class="form-control" id="inputEmail" type="email" placeholder="name@example.com" />
                      <label for="inputEmail">Email 주소</label>
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
  export default {
    data() {
      return {
        email: '',
      };
    },
    methods: {
      resetPassword() {
        // 비밀번호 재설정 로직을 구현합니다.
        // 예: 이메일 주소로 비밀번호 재설정 링크를 보냅니다.
        console.log("Password reset email sent to:", this.email);
        // API 호출 등을 통해 서버에 비밀번호 재설정 요청을 할 수 있습니다.
      },
    },
  };
  </script>