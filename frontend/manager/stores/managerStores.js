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
          "http://localhost:8080/manager/login",
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
        const response = await axios.get(
          "http://localhost:8080/jwt/reload",
          {
            headers: {
              AccessToken: accessToken,
              RefreshToken: refreshToken,
            },
          }
        );
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
          if (data.errorCode === "1006" || data.errorCode === 1006) {
            this.logout();
          }
        }
      } catch (error) {
        console.error("토큰 갱신 요청 중 오류 발생:", error);
        if (
          error.response &&
          error.response.data &&
          error.response.data.code === 1006
        ) {
          this.logout(); // 에러 코드에 따른 로그아웃 처리 함수 호출
        }
      }
    },
    logout() {
      // 로그아웃 처리
      sessionStorage.removeItem("accessToken");
      sessionStorage.removeItem("refreshToken");
      // 여기에 로그인 페이지로의 리다이렉션 로직을 추가할 수 있습니다.
      console.log("로그아웃 처리 완료");
    },
  },
});
axios.interceptors.response.use(
  (response) => response, // 성공 응답을 그대로 반환
  async (error) => {
    const originalRequest = error.config;
    const errorCode = error.response?.data?.code;
    const errorMessage = error.response?.data?.error;
    if (errorCode === "1006") {
      console.error("리프레시 토큰 만료:", errorMessage);
      const managerStore = useManagerStore();
      managerStore.logout();
      return Promise.reject(error);
    }
    if (
      error.response?.status === 401 &&
      errorCode === "1000" &&
      errorMessage === "만료된 토큰입니다" &&
      !originalRequest._retry
    ) {
      console.log("401 Unauthorized 에러 감지. 토큰 갱신 시도...");
      originalRequest._retry = true;
      const managerStore = useManagerStore();

      try {
        await managerStore.refreshToken();
        const newAccessToken = sessionStorage.getItem("accessToken");
        if (newAccessToken) {
          originalRequest.headers["AccessToken"] = newAccessToken;
          return axios(originalRequest); // 수정된 요청으로 재시도
        }
      } catch (refreshError) {
        console.error("토큰 갱신 실패:", refreshError);
      }
    }
    return Promise.reject(error);
  }
);
