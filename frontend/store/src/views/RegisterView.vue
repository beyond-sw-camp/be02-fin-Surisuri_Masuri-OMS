<template>
    <div id="layoutAuthentication_content">
      <main>
        <div class="container">
          <div class="row justify-content-center">
            <div class="col-lg-7">
              <div class="card shadow-lg border-0 rounded-lg mt-5">
                <div class="card-header">
                  <h3 class="text-center font-weight-light my-4">회원가입</h3>
                </div>
                <div class="card-body">
                  <form @submit.prevent="createAccount">
                    <div class="form-floating mb-3">
                      <input v-model="userName" class="form-control" id="inputName" type="text" placeholder="" />
                      <label for="inputName">성함</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="userEmail" class="form-control" id="inputEmail" type="email" placeholder="" />
                      <label for="inputEmail">Email</label>
                    </div>
                    <div class="row mb-3">
                      <div class="col-md-6">
                        <div class="form-floating mb-3 mb-md-0">
                          <input v-model="userPassword" class="form-control" id="inputPassword" type="password" placeholder="" />
                          <label for="inputPassword">비밀번호</label>
                        </div>
                      </div>
                      <div class="col-md-6">
                        <div class="form-floating mb-3 mb-md-0">
                          <input v-model="confirmPassword" class="form-control" id="inputPasswordConfirm" type="password" placeholder="" />
                          <label for="inputPasswordConfirm">비밀번호 재확인</label>
                        </div>
                      </div>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="userPhoneNo" class="form-control" id="inputPhoneNumber" type="text" placeholder="" />
                      <label for="inputPhoneNumber">핸드폰 번호</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="storeUuid" class="form-control" id="inputStoreUuid" type="text" placeholder="" />
                      <label for="inputStoreUuid">가맹점 UUID</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="storeAddr" class="form-control" id="inputStoreAddress" type="text" placeholder="" />
                      <label for="inputStoreAddress">가맹점 주소</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="storePhoneNo" class="form-control" id="inputStorePhoneNumber" type="text" placeholder="" />
                      <label for="inputStorePhoneNumber">가맹점 전화번호</label>
                    </div>
                    
                    <div class="mt-4 mb-0">
                      <div class="d-grid">
                        <button type="submit" class="btn btn-primary btn-block">회원가입 하기</button>
                      </div>
                    </div>
                  </form>
                </div>
                <div class="card-footer text-center py-3">
                  <div class="small">
                    <router-link to="/">로그인</router-link>
                  </div>
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
    name: 'RegisterView',
    data() {
      return {
        userName: '',
        userEmail: '',
        userPassword: '',
        confirmPassword: '',
        userPhoneNo: '',
        storeUuid: '',
        storeAddr: '',
        storePhoneNo: '',
      };
    },
    methods: {
      createAccount() {
        axios.post('http://localhost:8080/user/register', {
          userName: this.userName,
          userEmail: this.userEmail,
          userPassword: this.userPassword,
          confirmPassword: this.confirmPassword,
          userPhoneNo: this.userPhoneNo,
          storeUuid: this.storeUuid,
          storeAddr: this.storeAddr,
          storePhoneNo: this.storePhoneNo,
        })
        .then(response => {
          console.log('회원가입 성공', response.data);
          // 성공 처리 로직 추가
        })
        .catch(error => {
          console.error('회원가입 실패', error);
        });
      },
    },
  };
  </script>
  