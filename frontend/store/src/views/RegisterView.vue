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
                    <input v-model="userSignUpReq.userName" class="form-control" id="inputName" type="text" placeholder="" />
                    <label for="inputName">성함</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input v-model="userSignUpReq.userEmail" class="form-control" id="inputEmail" type="email" placeholder="" />
                    <label for="inputEmail">Email</label>
                  </div>
                  <div class="row mb-3">
                    <div class="col-md-6">
                      <div class="form-floating mb-3 mb-md-0">
                        <input v-model="userSignUpReq.userPassword" class="form-control" id="inputPassword" type="password" placeholder="" />
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
                    <input v-model="userSignUpReq.userPhoneNo" class="form-control" id="inputPhoneNumber" type="text" placeholder="" />
                    <label for="inputPhoneNumber">핸드폰 번호</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input v-model="userSignUpReq.storeUuid" class="form-control" id="inputStoreUuid" type="text" placeholder="" />
                    <label for="inputStoreUuid">가맹점 UUID</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input v-model="userSignUpReq.storeAddr" class="form-control" id="inputStoreAddress" type="text" placeholder="" />
                    <label for="inputStoreAddress">가맹점 주소</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input v-model="userSignUpReq.storePhoneNo" class="form-control" id="inputStorePhoneNumber" type="text" placeholder="" />
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
import { ref } from 'vue'; // ref 추가
import { useUserStore } from '/stores/userStore'; // 이 경로는 프로젝트에 맞게 수정해주세요

export default {
  name: 'RegisterView',
  setup() {
    const userStore = useUserStore();
    // 상태 관리에 사용되는 객체 내에서 직접 바인딩하기 위한 참조를 추가
    const confirmPassword = ref('');

    // 회원가입 함수 수정
    const createAccount = async () => {
      // 비밀번호 확인 로직 추가
      if (userStore.userSignUpReq.userPassword !== confirmPassword.value) {
        alert('비밀번호와 비밀번호 재확인이 일치하지 않습니다.');
        return;
      }

      try {
        // 회원가입 요청 실행
        const result = await userStore.createAccount();
        // 여기서 result는 userStore.createAccount()의 실행 결과에 따라 달라집니다.
        // 성공적으로 처리되었다면 해당하는 성공 메시지를,
        // 아니라면 예외 처리에 따른 메시지를 반환하도록 구현합니다.
        if (result.success) {
          alert('회원가입이 성공적으로 완료되었습니다.');
          // 회원가입 성공 후 로그인 페이지나 다른 페이지로 라우팅
        } else {
          // API 응답에 포함된 오류 메시지 사용
          alert(`회원가입 실패: ${result.message}`);
        }
      } catch (error) {
        // 예외 처리
        alert(`회원가입 중 오류가 발생했습니다: ${error.message}`);
      }
    };

    return {
      userSignUpReq: userStore.userSignUpReq,
      createAccount,
      confirmPassword, // 비밀번호 재확인을 위한 참조 반환
    };
  },
};
</script>
