<template>
  <div id="layoutAuthentication_content">
    <main>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-5">
            <div class="card shadow-lg border-0 rounded-lg mt-5">
              <div class="card-header"><h3 class="text-center font-weight-light my-4">본사 관리자 로그인</h3></div>
              <div class="card-body">
                <form @submit.prevent="loginSubmit">
                  <div class="form-floating mb-3">
                    <input v-model="userEmail" class="form-control"  placeholder="" />
                    <label for="inputEmail">Email</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input v-model="userPassword" class="form-control" id="inputPassword" type="password" placeholder="" />
                    <label for="inputPassword">비밀번호</label>
                  </div>
                  <div class="d-flex justify-content-between align-items-center mt-2">
                    <div>
                    </div>
                    <button type="submit" class="btn btn-primary">Login</button>
                  </div>
                </form>
              </div>
              <div class="card-footer text-center py-3">
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import { useManagerStore } from '/stores/managerStores';
import { useRouter } from 'vue-router';
import { useLoadingStore } from '../../stores/loadingStore';
import { ref } from 'vue';
import swal from 'sweetalert';
export default {
  setup() {
    const managerStore = useManagerStore();
    const router = useRouter();
    const loadingStore = useLoadingStore();
    const userEmail = ref('');
    const userPassword = ref('');

    const loginSubmit = async () => {
      try {
        loadingStore.showLoading();
        const success = await managerStore.login({
          id: userEmail.value,
          password: userPassword.value
        });
        loadingStore.hideLoading();
        if (success) {
          swal('로그인 성공', '', 'success'); // 성공 알림
          console.log('로그인 성공');
          router.push('/home');
        } else {
          swal('로그인 실패', '이메일 또는 비밀번호를 확인해 주세요.', 'error'); // 실패 알림
          console.log('로그인 실패');
        }
      } catch (error) {
        swal('로그인 요청 중 오류 발생', '다시 시도해 주세요.', 'error'); // 오류 알림
        console.error('로그인 요청 중 오류 발생:', error);
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
