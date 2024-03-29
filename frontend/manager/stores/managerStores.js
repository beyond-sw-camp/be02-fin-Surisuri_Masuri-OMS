import { defineStore } from 'pinia';
import axios from 'axios';

export const useManagerStore = defineStore({
  id: 'manager',
  state: () => ({
    token: sessionStorage.getItem('token') || null,
  }),
  actions: {
    async login({ id, password }) {
      try {
        const response = await axios.post('http://192.168.0.44/api/manager/login', { id, password });
        const { data } = response;
        if (data.isSuccess) {
          this.token = data.result.jwtToken;
          sessionStorage.setItem('token', this.token);
          return true;
        } else {
          console.error('로그인 실패:', data.message);
          return false;
        }
      } catch (error) {
        console.error('로그인 요청 중 오류 발생:', error);
        return false;
      }
    },
  },
});
