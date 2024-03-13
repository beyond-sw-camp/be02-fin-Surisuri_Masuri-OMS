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
                      <input v-model="name" class="form-control" id="inputName" type="text" placeholder="Enter your full name" />
                      <label for="inputName">성함</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="email" class="form-control" id="inputEmail" type="email" placeholder="name@example.com" />
                      <label for="inputEmail">Email 주소</label>
                    </div>
                    <div class="row mb-3">
                      <div class="col-md-6">
                        <div class="form-floating mb-3 mb-md-0">
                          <input v-model="password" class="form-control" id="inputPassword" type="password" placeholder="Create a password" />
                          <label for="inputPassword">비밀번호</label>
                        </div>
                      </div>
                      <!-- <div class="col-md-6">
                        <div class="form-floating mb-3 mb-md-0">
                          <input v-model="confirmPassword" class="form-control" id="inputPasswordConfirm" type="password" placeholder="Confirm password" />
                          <label for="inputPasswordConfirm">비밀번호 재확인</label>
                        </div>
                      </div> -->
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="phoneNumber" class="form-control" id="inputPhoneNumber" type="text" placeholder="Enter your phone number" />
                      <label for="inputPhoneNumber">핸드폰 번호</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="storeUuid" class="form-control" id="inputStoreUuid" type="text" placeholder="Enter store UUID" />
                      <label for="inputStoreUuid">가맹점 UUID</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="storeAddress" class="form-control" id="inputStoreAddress" type="text" placeholder="Enter store address" />
                      <label for="inputStoreAddress">가맹점 주소</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="storePhoneNumber" class="form-control" id="inputStorePhoneNumber" type="text" placeholder="Enter store phone number" />
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
  export default {
    name: 'RegisterView',
    data() {
      return {
        name: '',
        email: '',
        password: '',
        confirmPassword: '',
        phoneNumber: '',
        storeUuid: '',
        storeAddress: '',
        storePhoneNumber: '',
      };
    },
    methods: {
      createAccount() {
        console.log("Account creation requested with:", this.name, this.email, this.password, this.confirmPassword, this.phoneNumber, this.storeUuid, this.storeAddress, this.storePhoneNumber);
        // 여기에 계정 생성 처리 로직 구현
        // 예: API 호출을 통해 서버에 계정 생성 요청
      },
    },
  };
  </script>