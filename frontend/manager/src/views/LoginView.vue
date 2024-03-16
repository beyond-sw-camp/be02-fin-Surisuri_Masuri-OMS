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
                    <input v-model="userEmail" class="form-control" id="inputEmail" type="email" placeholder="" />
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
import { ref } from 'vue';

export default {
  setup() {
    const managerStore = useManagerStore();
    const router = useRouter();

    const userEmail = ref('');
    const userPassword = ref('');

    const loginSubmit = async () => {
      try {
        const success = await managerStore.login({
          id: userEmail.value,
          password: userPassword.value
        });

        if (success) {
          console.log('로그인 성공');
          router.push('/home');
        } else {
          console.log('로그인 실패');
        }
      } catch (error) {
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
