import { defineStore } from 'pinia';
import axios from 'axios';

export const useUserStore = defineStore({
  id: 'user',
  state: () => ({
    userSignUpReq: {
      userName: '',
      userEmail: '',
      userPassword: '',
      confirmPassword: '',
      userPhoneNo: '',
      storeUuid: '',
      storeAddr: '',
      storePhoneNo: '',
    },
    token: null,
  }),
  actions: {
    async createAccount() {
      try {
        const response = await axios.post('http://localhost:8080/user/register', this.userSignUpReq, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        console.log('회원가입 성공', response.data);
        // 성공 처리 로직 추가
      } catch (error) {
        console.error('회원가입 실패', error);
      }
    },
    async login(loginReq) {
      try {
        const response = await axios.post('http://localhost:8080/user/login', loginReq, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        const { data } = response;
        if (data.isSuccess) {
          this.token = data.result.jwtToken;
          // 토큰을 세션 스토리지에 저장
          sessionStorage.setItem('token', this.token);
          console.log('로그인 성공');
          return true; // 로그인 성공
        } else {
          console.error('로그인 실패:', data.message);
          return false; // 로그인 실패
        }
      } catch (error) {
        console.error('로그인 요청 중 오류 발생:', error);
        return false; // 로그인 요청 오류
      }
    },
  },
});
