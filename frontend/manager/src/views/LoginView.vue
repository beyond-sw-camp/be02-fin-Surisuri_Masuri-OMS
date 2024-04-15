<template>
  <div id="layoutAuthentication_content">
    <main>
      <p class="text-center text-info" style="font-weight: bold; font-size: 16px; margin-top: -10px;">
        아이디: test01 / 비밀번호: Qwer1234! 로 로그인하여 모든 서비스를 테스트 해보세요.
      </p>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-5">
            <div class="card shadow-lg border-0 rounded-lg mt-5">
              <div class="card-header">
                <h3 class="text-center font-weight-bold my-4" style="font-size: 24px;">
                  본사 관리자 로그인
                </h3>
              </div>
              <div class="card-body">
                <form @submit.prevent="loginSubmit">
                  <div class="form-floating mb-3">
                    <input
                        v-model="userEmail"
                        class="form-control"
                        id="inputEmail"
                        placeholder="Id"
                    />
                    <label for="inputEmail">Id</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input
                        v-model="userPassword"
                        class="form-control"
                        id="inputPassword"
                        type="password"
                        placeholder="Password"
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
    const userEmail = ref("test01");
    const userPassword = ref("Qwer1234!");

    const loginSubmit = async () => {
      try {
        loadingStore.showLoading();
        const result = await managerStore.login({
          id: userEmail.value,
          password: userPassword.value,
        });
        loadingStore.hideLoading();
        if (result === true) {
          swal("로그인 성공", "", "success");
          router.push("/home");
        } else if (typeof result === "object" && result.errorCode === 900) {
          swal("로그인 실패", result.errorMessage, "error");
        } else {
          const errorMessage = getErrorMessage(result);
          swal("로그인 실패", errorMessage, "error");
        }
      } catch (error) {
        swal(
            "로그인 요청 중 오류 발생",
            error.message || "다시 시도해 주세요.",
            "error"
        );
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
.text-info {
  color: #17a2b8; /* 부트스트랩의 info 색상 코드를 사용 */
}
</style>
