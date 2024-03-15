<template class ="LoginView">
    <div id="layoutAuthentication_content">
      <main>
        <div class="container">
          <div class="row justify-content-center">
            <div class="col-lg-5">
              <div class="card shadow-lg border-0 rounded-lg mt-5">
                <div class="card-header"><h3 class="text-center font-weight-light my-4">가맹점 관리자 로그인</h3></div>
                <div class="card-body">
                  <form @submit.prevent="loginSubmit">
                    <div class="form-floating mb-3">
                      <input v-model="userEmail" class="form-control" id="inputEmail" type="email" placeholder="" />
                      <label for="inputEmail">Email</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="userPassword" class="form-control" id="inputPassword" type="password" placeholder="" />
                      <label for="inputPassword">비밀번호</label>
                    </div>
                    <div class="d-flex justify-content-between align-items-center mt-2">
                    <div>
                      <router-link class="small me-2" to="/password">비밀번호를 잊으셨나요?</router-link>
                      <router-link class="small" to="/loginReset">아이디를 잊으셨나요?</router-link>
                    </div>
                    <button type="submit" class="btn btn-primary">로그인</button>
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
      userEmail: '',
      userPassword: '',
      rememberPassword: false,
    };
  },

  methods: {
    async loginSubmit() {
      try {
        const response = await axios.post('http://localhost:8080/user/login', {
          id: this.userEmail,
          password: this.userPassword,
          
        }, {
          headers: {
            'Content-Type': 'application/json'
          }
        });

        const token = response.data.result.jwtToken;
        sessionStorage.setItem('token', token);
        console.log('로그인 성공');
        this.$router.push('/home');  // 로그인 후 메인 페이지로 이동

      } catch (error) {
        console.error('로그인 실패', error);
        // 로그인 실패 시에 대한 처리
      }
    }
  }
};
</script>

<style>

#layoutAuthentication_content {
  display: flex;
  flex-direction: column; /* 세로 방향으로 스택 */
  justify-content: center; /* 세로 방향 중앙 정렬 */
  min-height: 100vh;
  transform: translateY(-8%); /* 전체 뷰포트 높이 */
}
</style>