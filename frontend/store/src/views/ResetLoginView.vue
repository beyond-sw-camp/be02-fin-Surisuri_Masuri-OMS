<template>
    <div id="layoutAuthentication_content">
      <main>
        <div class="container">
          <div class="row justify-content-center">
            <div class="col-lg-5">
              <div class="card shadow-lg border-0 rounded-lg mt-5">
                <div class="card-header">
                  <h3 class="text-center font-weight-light my-4">아이디 찾기</h3>
                </div>
                <div class="card-body">
                  <form @submit.prevent="findId">
                    <div class="form-floating mb-3">
                      <input v-model="userName" class="form-control" id="inputName" type="text" placeholder="" />
                      <label for="inputName">성함</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="userPhoneNo" class="form-control" id="inputPhoneNumber" type="tel" placeholder="" />
                      <label for="inputPhoneNumber">핸드폰 번호</label>
                    </div>
                    <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                      <router-link class="small" to="/">로그인</router-link>
                      <button type="submit" class="btn btn-primary">아이디 찾기</button>
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

const baseURL = 'http://192.168.0.45';

export default {
  data() {
    return {
      userName: '',
      userPhoneNo: '',
      foundId: '',
    };
  },
  methods: {
    async findId() {
      if (!this.userName.trim() || !this.userPhoneNo.trim()) {
        alert('이름과 핸드폰 번호를 모두 입력해주세요.');
        return;
      }
      
      try {
        const response = await axios.get(baseURL + '/user/findEmail', {
          params: {
            userName: this.userName,
            userPhoneNo: this.userPhoneNo,
          }
        });

        if (response.data && response.data.result && response.data.result.userEmail) {
          this.foundId = response.data.result.userEmail;
          alert(`찾은 아이디: ${this.foundId}`);
        } else {
          alert('해당 정보와 일치하는 아이디가 없습니다.');
        }
      } catch (error) {
        const errorMessage = getErrorMessage(error.response && error.response.data ? error.response.data.errorCode : null);
        alert(`아이디를 찾는 동안 오류가 발생했습니다.\n${errorMessage}`);
      }
    },
  },
};
</script>
