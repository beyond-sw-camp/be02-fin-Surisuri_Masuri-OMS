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
  },
});
