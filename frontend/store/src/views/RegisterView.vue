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
                    <input
                      v-model="userSignUpReq.userName"
                      class="form-control"
                      id="inputName"
                      type="text"
                      placeholder=""
                    />
                    <label for="inputName">성함</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input
                      v-model="userSignUpReq.userEmail"
                      class="form-control"
                      id="inputEmail"
                      type="email"
                      placeholder=""
                    />
                    <label for="inputEmail">Email</label>
                  </div>
                  <div class="row mb-3">
                    <div class="col-md-6">
                      <div class="form-floating mb-3 mb-md-0">
                        <input
                          v-model="userSignUpReq.userPassword"
                          class="form-control"
                          id="inputPassword"
                          type="password"
                          placeholder=""
                        />
                        <label for="inputPassword">비밀번호</label>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-floating mb-3 mb-md-0">
                        <input
                          v-model="confirmPassword"
                          class="form-control"
                          id="inputPasswordConfirm"
                          type="password"
                          placeholder=""
                        />
                        <label for="inputPasswordConfirm"
                          >비밀번호 재확인</label
                        >
                      </div>
                    </div>
                  </div>
                  <div class="form-floating mb-3">
                    <input
                      v-model="userSignUpReq.userPhoneNo"
                      class="form-control"
                      id="inputPhoneNumber"
                      type="text"
                      placeholder=""
                    />
                    <label for="inputPhoneNumber">핸드폰 번호</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input
                      v-model="userSignUpReq.storeUuid"
                      class="form-control"
                      id="inputStoreUuid"
                      type="text"
                      placeholder=""
                    />
                    <label for="inputStoreUuid">가맹점 UUID</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input
                      v-model="userSignUpReq.storeAddr"
                      class="form-control"
                      id="inputStoreAddress"
                      type="text"
                      placeholder=""
                    />
                    <label for="inputStoreAddress">가맹점 주소</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input
                      v-model="userSignUpReq.storePhoneNo"
                      class="form-control"
                      id="inputStorePhoneNumber"
                      type="text"
                      placeholder=""
                    />
                    <label for="inputStorePhoneNumber">가맹점 전화번호</label>
                  </div>

                  <div class="mt-4 mb-0">
                    <div class="d-grid">
                      <button type="submit" class="btn btn-primary btn-block">
                        회원가입 하기
                      </button>
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
import { ref } from "vue";
import { useUserStore } from "../../stores/userStore.js";
import { getErrorMessage } from "../utils/error.js"; // 에러 처리 함수 임포트
import { useRouter } from 'vue-router';

export default {
  name: "RegisterView",
  setup() {
    const router = useRouter();
    const userStore = useUserStore();
    const confirmPassword = ref("");

    const createAccount = async () => {
      if (userStore.userSignUpReq.userPassword !== confirmPassword.value) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        return;
      }

      const result = await userStore.createAccount();
      if (result === true) {
        alert("회원가입이 성공적으로 완료되었습니다. 이메일 인증을 완료해주세요");
        router.push('/login');
      } else {
        const errorMessage = getErrorMessage(result); // 에러 코드를 이용해 에러 메시지를 얻음
        alert(errorMessage); // 에러 메시지 표시
      }
    };

    return {
      userSignUpReq: userStore.userSignUpReq,
      createAccount,
      confirmPassword,
    };
  },
};
</script>
