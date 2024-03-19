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
        const response = await axios.get('http://localhost:8080/user/findEmail', {
          params: {
            userName: this.userName,
            userPhoneNo: this.userPhoneNo,
          }
        });

        if (response.data && response.data.result && response.data.result.userEmail) {
          this.foundId = response.data.result.userEmail;
          alert(`찾은 아이디: ${this.foundId}`); // 사용자에게 찾은 ID를 알립니다.
        } else {
          // 응답은 성공했으나, 유저 이메일 정보가 없는 경우
          alert('해당 정보와 일치하는 아이디가 없습니다.');
        }
      } catch (error) {
        let errorMessage = '아이디를 찾는 동안 오류가 발생했습니다.';
        if (error.response && error.response.data && error.response.data.message) {
          errorMessage += `\n${error.response.data.message}`;
        } else {
          errorMessage += '\n서버 오류가 발생했습니다.';
        }
        alert(errorMessage);
      }
    },
  },
};
</script>



<!-- <script>
import axios from 'axios';

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
      try {
        const response = await axios.get('http://localhost:8080/user/findEmail', {
          name: this.userName,
          phoneNumber: this.userPhoneNo,
        });

        // 응답에서 받은 아이디를 변수에 할당합니다.
        this.foundId = response.data.userEmail;

        // 아이디를 화면에 보여줍니다.
        console.log('찾은 아이디:', this.foundId);
      } catch (error) {
        // 오류가 발생한 경우 오류를 처리합니다.
        console.error('아이디를 찾는 동안 오류가 발생했습니다:', error);
      }
    },
  },
};
</script> -->