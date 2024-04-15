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


<script>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "../../stores/userStore.js";
import { useLoadingStore } from '../../stores/loadingStore';
import { getErrorMessage } from "../utils/error.js";
import swal from 'sweetalert';
export default {
  setup() {
    const router = useRouter();
    const userStore = useUserStore(); // Pinia 스토어 사용
    const loadingStore = useLoadingStore(); 
    const userEmail = ref("");
    const userPassword = ref("");

    const loginSubmit = async () => {

      loadingStore.showLoading();
      const result = await userStore.login({
        id: userEmail.value,
        password: userPassword.value,
      });
      loadingStore.hideLoading();
      if (result === true) {
        swal('로그인 성공', '', 'success'); // 로그인 성공 알림
        router.push("/home");
      } else if (typeof result === "object" && result.errorCode === 900) {
          // 오류 코드 900에 대한 서버 메시지 사용
          swal("로그인 실패", result.errorMessage, "error");
        } else {
          // 다른 오류 코드의 경우, 표준 오류 메시지 사용
          const errorMessage = getErrorMessage(result);
          swal("로그인 실패", errorMessage, "error");
        }
      }

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
