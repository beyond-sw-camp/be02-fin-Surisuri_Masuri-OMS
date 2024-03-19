<template>
  <div id="layoutAuthentication_content">
    <main>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-5">
            <div class="card shadow-lg border-0 rounded-lg mt-5">
              <div class="card-header">
                <h3 class="text-center font-weight-light my-4">비밀번호 재설정</h3>
              </div>
              <div class="card-body">
                <div class="small mb-3 text-muted">새로운 비밀번호를 입력하고, 확인을 위해 다시 입력해 주세요.</div>
                <form @submit.prevent="submitNewPassword">
                  <div class="form-floating mb-3">
                    <input v-model="resetPasswordReq.userPassword" class="form-control" id="newPassword" type="password" placeholder="New Password" />
                    <label for="newPassword">새 비밀번호</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input v-model="confirmPassword" class="form-control" id="confirmPassword" type="password" placeholder="Confirm Password" />
                    <label for="confirmPassword">새 비밀번호 확인</label>
                  </div>
                  <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                    <router-link class="small" to="/">로그인 페이지로 돌아가기</router-link>
                    <button type="submit" class="btn btn-primary">비밀번호 재설정</button>
                  </div>
                </form>
              </div>
              <div class="card-footer text-center py-3">
                <div class="small"><router-link to="/register">계정이 없으신가요? 회원가입하기</router-link></div>
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
      confirmPassword: '',
      resetPasswordReq: {
        userPassword: ''
      },
    };
  },
  methods: {
    async submitNewPassword() {
      if (this.resetPasswordReq.userPassword === this.confirmPassword) {
        try {
          const formData = new FormData();
          formData.append('userPassword', this.resetPasswordReq.userPassword);
          const response = await axios.patch(`http://localhost:8080/user/resetPassword/${this.$route.params.idx}`, formData);
          
          // 성공 알림
          alert("비밀번호가 성공적으로 재설정되었습니다.");
          console.log(response.data);
          
          // 추가적으로 로그인 페이지로 리다이렉트하는 로직을 추가할 수 있습니다.
          // this.$router.push('/login');
        } catch (error) {
          // 실패 알림
          alert("비밀번호 재설정 요청 실패. 다시 시도해 주세요.");
          console.error('비밀번호 재설정 요청 실패:', error);
        }
      } else {
        // 비밀번호 불일치 알림
        alert("입력한 비밀번호가 서로 일치하지 않습니다. 다시 확인해 주세요.");
        console.log("Passwords do not match.");
      }
    },
  },
};
</script>

