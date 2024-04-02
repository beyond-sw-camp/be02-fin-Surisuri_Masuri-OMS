<template>
  <div id="layoutAuthentication_content">
    <main>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-5">
            <div class="card shadow-lg border-0 rounded-lg mt-5">
              <div class="card-header"><h3 class="text-center font-weight-light my-4">가맹점 관리자 로그인</h3></div>
              <div class="card-body">
                <form @submit.prevent="loginSubmit">
                  <div class="form-floating mb-3">
                    <input v-model="userEmail" class="form-control" id="inputEmail" type="email" placeholder="" />
                    <label for="inputEmail">Email</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input v-model="userPassword" class="form-control" id="inputPassword" type="password" placeholder="" />
                    <label for="inputPassword">비밀번호</label>
                  </div>
                  <div class="d-flex justify-content-between align-items-center mt-2">
                    <div>
                      <router-link class="small" to="/loginreset">아이디를 잊으셨나요?</router-link><br>
                      <router-link class="small me-2" to="/password">비밀번호를 잊으셨나요?</router-link>
                    </div>
                    <button type="submit" class="btn btn-primary">로그인</button>
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

User
<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "../../stores/userStore.js";
import { getErrorMessage } from "../utils/error.js";
export default {
  setup() {
    const router = useRouter();
    const userStore = useUserStore(); // Pinia 스토어 사용

    const userEmail = ref("");
    const userPassword = ref("");

    const loginSubmit = async () => {
      const result = await userStore.login({
        id: userEmail.value,
        password: userPassword.value,
      });
      if (result === true) {
        alert("로그인 성공"); // 로그인 성공 알림
        router.push("/home");
      } else {
        // getErrorMessage 함수를 사용하여 에러 메시지를 얻음
        const errorMessage = getErrorMessage(result); // 결과값이 에러 코드일 것임
        alert(errorMessage); // 에러 메시지를 알림 창으로 표시
      }
    };

    return { userEmail, userPassword, loginSubmit };
  },
};
</script>

<style>
#layoutAuthentication_content {
  display: flex;
  flex-direction: column; /* 세로 방향으로 스택 */
  justify-content: center; /* 세로 방향 중앙 정렬 */
  min-height: 100vh;
  transform: translateY(-8%); /* 전체 뷰포트 높이 */
}
</style>
