import { defineStore } from "pinia";
import axios from "axios";

export const useManagerStore = defineStore({
  id: "manager",
  state: () => ({
    accessToken: sessionStorage.getItem("accessToken") || null,
    refreshToken: sessionStorage.getItem("refreshToken") || null,
  }),
  getters: {
    // accessToken을 반환하는 getter
    currentAccessToken: (state) => state.accessToken,
  },
  actions: {
    async login({ id, password }) {
      try {
        const response = await axios.post(
          "http://121.140.125.34:11114/api/manager/login",
          { id, password }
        );
        const { data } = response;
        if (data.isSuccess) {
          this.accessToken = data.result.accessToken;
          this.refreshToken = data.result.refreshToken;

          sessionStorage.setItem("accessToken", this.accessToken);
          sessionStorage.setItem("refreshToken", this.refreshToken);

          return true;
        } else {
          console.error("로그인 실패:", data.message);
          return false;
        }
      } catch (error) {
        console.error("로그인 요청 중 오류 발생:", error);
        return false;
      }
    },
    async refreshToken() {
      console.log("refreshToken 함수가 호출되었습니다.");
      const accessToken = sessionStorage.getItem("accessToken");
      const refreshToken = sessionStorage.getItem("refreshToken");
      console.log(
        `Before request: AccessToken: ${accessToken}, RefreshToken: ${refreshToken}`
      );
      try {
        const response = await axios.get("http://121.140.125.34:11114/api/jwt/reload", {
          headers: {
            AccessToken: accessToken,
            RefreshToken: refreshToken,
          },
        });
        const { data } = response;
        if (data.isSuccess) {
          console.log("토큰 갱신 성공:", data.message);

          // 성공적으로 새로운 토큰을 받았을 때, 상태 업데이트
          sessionStorage.setItem("accessToken", data.result.accessToken);

          axios.defaults.headers.common["AccessToken"] =
            data.result.accessToken;

          // Pinia 스토어의 상태 업데이트
          this.accessToken = data.result.accessToken;
          this.refreshToken = data.result.refreshToken;
        } else {
          console.error("토큰 갱신 실패:", data);
        }
      } catch (error) {
        console.error("토큰 갱신 요청 중 오류 발생:", error);
      }
    },
  },
});
axios.interceptors.response.use(
  (response) => response, // 성공 응답을 그대로 반환
  async (error) => {
    const originalRequest = error.config;
    if (error.response?.status === 401 && !originalRequest._retry) {
      console.log("401 Unauthorized 에러 감지. 토큰 갱신 시도...");
      originalRequest._retry = true;
      const managerStore = useManagerStore();

      try {
        await managerStore.refreshToken();
        const newAccessToken = sessionStorage.getItem("accessToken");
        if (newAccessToken) {
          originalRequest.headers["AccessToken"] = newAccessToken;
          return axios(originalRequest); // 수정된 요청으로 재시도
        } else {
          console.error("새로운 AccessToken을 받지 못함");
          // 적절한 사용자 피드백 또는 로그아웃 처리
        }
      } catch (refreshError) {
        // refreshToken 함수에서 오류가 발생했을 때 처리
        console.error("토큰 갱신 실패:", refreshError);
        // 적절한 사용자 피드백 또는 로그아웃 처리
      }
    }
    return Promise.reject(error);
  }
);
