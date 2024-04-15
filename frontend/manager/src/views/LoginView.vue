<template>
  <div id="layoutAuthentication_content">
    <main>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-5">
            <div class="card shadow-lg border-0 rounded-lg mt-5">
              <div class="card-header">
                <h3 class="text-center font-weight-light my-4">
                  본사 관리자 로그인
                </h3>
              </div>
              <div class="card-body">
                <form @submit.prevent="loginSubmit">
                  <div class="form-floating mb-3">
                    <input
                      v-model="userEmail"
                      class="form-control"
                      placeholder=""
                    />
                    <label for="inputEmail">Id</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input
                      v-model="userPassword"
                      class="form-control"
                      id="inputPassword"
                      type="password"
                      placeholder=""
                    />
                    <label for="inputPassword">Password</label>
                  </div>
                  <div
                    class="d-flex justify-content-between align-items-center mt-2"
                  >
                    <div></div>
                    <button type="submit" class="btn btn-primary">Login</button>
                  </div>
                </form>
              </div>
              <div class="card-footer text-center py-3"></div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import { useManagerStore } from "../../stores/managerStores";
import { useRouter } from "vue-router";
import { useLoadingStore } from "../../stores/loadingStore";
import { ref } from "vue";
import { getErrorMessage } from "../utils/error.js";
import swal from "sweetalert";
export default {
  setup() {
    const router = useRouter();
    const managerStore = useManagerStore();
    const loadingStore = useLoadingStore();
    const userEmail = ref("");
    const userPassword = ref("");

    const loginSubmit = async () => {
      try {
        loadingStore.showLoading();
        const result = await managerStore.login({
          id: userEmail.value,
          password: userPassword.value,
        });
        loadingStore.hideLoading();
        if (result === true) {
          swal("로그인 성공", "", "success"); // 로그인 성공 알림
          router.push("/home");
        } else if (typeof result === "object" && result.errorCode === 900) {
          // 오류 코드 900에 대한 서버 메시지 사용
          swal("로그인 실패", result.errorMessage, "error");
        } else {
          // 다른 오류 코드의 경우, 표준 오류 메시지 사용
          const errorMessage = getErrorMessage(result);
          swal("로그인 실패", errorMessage, "error");
        }
      } catch (error) {
        swal(
          "로그인 요청 중 오류 발생",
          error.message || "다시 시도해 주세요.",
          "error"
        ); // 오류 알림
      }
    };

    return { userEmail, userPassword, loginSubmit };
  },
};
</script>

<style>
#layoutAuthentication_content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  min-height: 100vh;
  transform: translateY(-8%);
}
</style>
