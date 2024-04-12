import { defineStore } from "pinia";
import axios from "axios";

const baseURL = "http://121.140.125.34:11113/api";

export const useUserStore = defineStore({
  id: "user",
  state: () => ({
    userSignUpReq: {
      userName: "",
      userEmail: "",
      userPassword: "",
      confirmPassword: "",
      userPhoneNo: "",
      storeUuid: "",
      storeAddr: "",
      storePhoneNo: "",
    },
    AccessTokenToken: sessionStorage.getItem("accessToken") || null,
    refreshToken: sessionStorage.getItem("refreshToken") || null,
    discardedProduct: "",
  }),
  actions: {
    async createAccount() {
      try {
        const response = await axios.post(
          `${baseURL}/user/register`,
          this.userSignUpReq,
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        );
        console.log("회원가입 성공", response.data);
        if (response.data.isSuccess) {
          // isSuccess 값이 true인 경우, 회원가입 성공으로 간주
          return true; // 성공적으로 회원가입 완료
        } else {
          return response.data.code || "unknown_error";
        }
      } catch (error) {
        console.error("회원가입 실패", error);
        if (error.response && error.response.data && error.response.data.code) {
          // 서버로부터 받은 응답에서 에러 코드가 있을 경우
          return error.response.data.code; // 에러 코드 반환
        }
        return "unknown_error"; // 에러 코드가 없는 경우 기본 에러 코드 반환
      }
    },

    async login(loginReq) {
      try {
        const response = await axios.post(`${baseURL}/user/login`, loginReq, {
          headers: {
            "Content-Type": "application/json",
          },
        });
        const { data } = response;
        if (data.isSuccess) {
          this.accessToken = data.result.accessToken;
          this.refreshToken = data.result.refreshToken;
          this.discardedProduct = data.result.discardedProduct;

          sessionStorage.setItem("accessToken", this.accessToken);
          sessionStorage.setItem("refreshToken", this.refreshToken);

          return true; // 로그인 성공
        } else {
          // 서버 응답에 따라 에러 코드 반환
          return data.errorCode; // 예를 들어, 서버 응답에서 제공하는 에러 코드
        }
      } catch (error) {
        if (error.response && error.response.data && error.response.data.code) {
          return error.response.data.code; // 에러 코드 추출
        }
        return "unknown_error"; // 에러 코드가 없는 경우 기본 에러 코드 반환
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
        const response = await axios.get(`${baseURL}/jwt/reload`, {
          headers: {
            AccessToken: accessToken,
            RefreshToken: refreshToken,
          },
        });
        const { data } = response;
        if (data.isSuccess) {
          console.log("토큰 갱신 성공:", data.message);

          sessionStorage.setItem("accessToken", data.result.accessToken);

          // axios.defaults.headers.common["AccessToken"] = this.accessToken;

          this.accessToken = data.result.accessToken;
          this.refreshToken = data.result.refreshToken;
        } else {
          console.error("토큰 갱신 실패:", data.message);
          if (data.errorCode === "1006" || data.errorCode === 1006) {
            this.logout(); // 리프레시 토큰 만료 에러 코드에 따른 로그아웃 처리 함수 호출
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

// Axios 응답 인터셉터에 토큰 갱신 로직 추가
axios.interceptors.response.use(
  (response) => response, // 성공 응답을 그대로 반환
  async (error) => {
    const originalRequest = error.config;
    const errorCode = error.response?.data?.code;
    const errorMessage = error.response?.data?.error;

    if (errorCode === "1006") {
      console.error("리프레시 토큰 만료:", errorMessage);
      const userStore = useUserStore();
      userStore.logout();
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
      const userStore = useUserStore();

      try {
        await userStore.refreshToken();
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
