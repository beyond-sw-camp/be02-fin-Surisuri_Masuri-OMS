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
      try {
        // 서버로 비밀번호 재설정 요청을 보냅니다.
        const response = await axios.post('http://localhost:8080/user/findPassword', {
          userEmail: this.userEmail,
        });

        // 요청이 성공적으로 처리되었을 때의 로직을 여기에 작성합니다.
        console.log('비밀번호 재설정 요청이 성공적으로 처리되었습니다.', response.data);

        // 예를 들어, 사용자에게 알림을 보여줄 수 있습니다.
      } catch (error) {
        // 요청이 실패했을 때의 오류 처리 로직을 여기에 작성합니다.
        console.error('비밀번호 재설정 요청 중 오류가 발생했습니다:', error.response.data);
      }
    },
  },
};
</script>