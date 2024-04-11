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
                    <input v-model="userName" class="form-control" id="inputName" type="text" placeholder="" />
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
import { getErrorMessage } from '../utils/error.js'; // 에러 메시지 처리 함수 임포트
import swal from 'sweetalert';
const baseURL = 'http://121.140.125.34:11113/api';

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
        swal('이름과 이메일을 모두 입력해주세요.');
        return;
      }

      try {
        const findUserPasswordReq = {
          userName: this.userName,
          userEmail: this.userEmail,
        };

        const response = await axios.post(baseURL + '/user/findPassword', findUserPasswordReq, {
          headers: {
            'Content-Type': 'application/json',
          },
        });

        if (response.data.result) {
          swal('비밀번호 재설정 링크가 이메일로 전송되었습니다. 메일을 확인해주세요.');
        } else {
          swal('비밀번호 재설정 요청을 처리할 수 없습니다. 입력 정보를 확인해주세요.');
        }
      } catch (error) {
        const errorMessage = getErrorMessage(error.response && error.response.data ? error.response.data.errorCode : null);
        swal(`비밀번호 재설정 요청 중 오류가 발생했습니다.\n${errorMessage}`);
      }
    },
  },
};
</script>

