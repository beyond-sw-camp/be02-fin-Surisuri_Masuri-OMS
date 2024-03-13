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
                      <input v-model="name" class="form-control" id="inputName" type="text" placeholder="홍길동" />
                      <label for="inputName">성함</label>
                    </div>
                    <div class="form-floating mb-3">
                      <input v-model="phoneNumber" class="form-control" id="inputPhoneNumber" type="tel" placeholder="010-1234-5678" />
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
  export default {
    data() {
      return {
        name: '',
        phoneNumber: '',
      };
    },
    methods: {
      findId() {
        // 여기에 이름과 핸드폰 번호를 사용한 아이디 찾기 로직을 구현합니다.
        // 예시: 서버에 요청을 보내어 입력받은 정보와 일치하는 계정의 아이디를 조회합니다.
        console.log("찾는 중:", this.name, this.phoneNumber);
      },
    },
  };
  </script>