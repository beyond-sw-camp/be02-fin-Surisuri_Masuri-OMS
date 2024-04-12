<template>
  <div class="container-fluid main-container">
    <!--  Row 1 -->
    <div class="row">
      <div class="col-lg-8 d-flex align-items-strech">
        <div class="card w-100" id="chart-card">
          <div class="card-body">
            <div
              class="d-sm-flex d-block align-items-center justify-content-between mb-9"
            >
              <div class="mb-3 mb-sm-0">
                <h5 class="card-title fw-semibold">상품 잔여 재고</h5>
              </div>
            </div>
            <canvas ref="MyChart"></canvas>
          </div>
        </div>
      </div>
      <div class="col-lg-4">
        <div class="row">
          <div class="col-lg-12">
            <!-- Yearly Breakup -->
            <div class="card overflow-hidden">
              <div class="card-body p-4" id="chart-card1">
                <div class="row align-items-center">
                  <div class="col-8">
                    <h4 class="fw-semibold mb-3">많이 팔린 상품</h4>
                    <div class="d-flex align-items-center"></div>
                  </div>
                  <div class="col-4">
                    <div class="d-flex justify-content-center">
                      <canvas ref="myPieChart"></canvas>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-12">
            <!-- Monthly Earnings -->
            <div class="card">
              <div class="card-body" id="chart-card2">
                <div class="row alig n-items-start">
                  <div class="col-8" id="chart-card2">
                    <h5 class="card-title mb-9 fw-semibold">월별 매출</h5>
                  </div>
                </div>
              </div>
              <canvas ref="myLineChart"></canvas>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-4 d-flex align-items-stretch">
        <div class="card w-100">
          <div class="card-body p-4">
            <div class="mb-4">
              <h5 class="card-title fw-semibold notice-title">공지사항</h5>
              <!-- 공지사항 제목 표시 시작 -->
              <table class="table">
                <thead>
                  <tr>
                    <th scope="col">번호</th>
                    <th scope="col">제목</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="(notice, index) in notices.slice(0, 4)"
                    :key="notice.noticeIdx"
                  >
                    <th scope="row">{{ index + 1 }}</th>
                    <td>
                      <!-- 문의사항 제목에 router-link 적용하여 상세 페이지로 이동 -->
                      <router-link
                        :to="{
                          name: 'NoticeDetail',
                          query: {
                            idx: notice.noticeIdx,
                            title: notice.title,
                            content: notice.content,
                            category: notice.category,
                          },
                        }"
                        >{{ notice.title }}</router-link
                      >
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <div class="col-lg-8 d-flex align-items-stretch">
        <div class="card w-100">
          <div class="card-body p-4">
            <swiper
              class="mySwiper"
              :options="{ navigation: { clickable: true } }"
            >
              <swiper-slide v-for="image in images" :key="image">
                <img :src="image" alt="사진 슬라이드" />
              </swiper-slide>
            </swiper>
            <h5 class="card-title fw-semibold mb-4"></h5>
            <div class="table-responsive">
              <table class="table text-nowrap mb-0 align-middle"></table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 우측 하단 알림 -->
    <div
      class="fixed-bottom px-3 py-2 bg-dark text-light"
      v-if="showNotification"
    >
      <div class="container-fluid">
        <div class="row align-items-center">
          <div class="col">
            <p class="mb-0">{{ notificationText }}</p>
          </div>
          <div class="col-auto">
            <button
              type="button"
              class="btn-close btn-close-white"
              @click="hideNotification"
            ></button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Chart, registerables } from "chart.js";
Chart.register(...registerables);
import { Swiper, SwiperSlide } from "swiper/vue";
import "swiper/css";
import axios from "axios";
import { useUserStore } from "/stores/userStore"; // 스토어 경로는 프로젝트에 맞게 조정하세요.

export default {
  data() {
    return {
      images: [
        require("@/assets/splide2.png"),
        require("@/assets/splide1.png"),
      ],
      notices: [],
      barChartData: {
        labels: [
          "블루마운틴 원두",
          "업소용 우유 (1L)",
          "플라스틱 빨대",
          "매장용 머그컵",
          "휘핑 크림",
          "시그니처 텀블러",
          "에스프레소",
          "카페 라떼",
          "카푸치노",
          "아메리카노",
        ],
        datasets: [
          {
            label: "재고 수량",
            data: [18, 19, 15, 12, 17, 7, 14, 16, 9, 11],
            backgroundColor: [
              "rgba(255, 255, 16, 0.49)", // 라이트 노란색
              "rgba(87, 255, 16, 0.49)", // 라이트 노란색
              "rgba(211, 81, 16, 0.49)", // 라이트 노란색
              "rgba(211, 81, 171, 0.49)", // 라이트 노란색
              "rgba(72, 81, 171, 0.49)", // 라이트 노란색
              "rgba(91, 221, 171, 0.49)", // 라이트 노란색
              "rgba(91, 221, 89, 0.49)", // 라이트 노란색
              "rgba(205, 86, 89, 0.49)", // 라이트 노란색
              "rgba(201, 202, 222, 1)", // 라이트 노란색
              "rgba(128, 202, 222, 1)", // 라이트 노란색
            ],
            borderWidth: 1,
            borderRadius: {
              topLeft: 30,
              topRight: 30,
              bottomLeft: 0,
              bottomRight: 0,
            },
            barThickness: 15, // 바의 너비를 더 줄여서 더 얇게 만듭니다.
          },
        ],
      },
      pieChartData: {
        labels: [
          "아메리카노",
          "바닐라라떼",
          "토피넛라떼",
          "초코프라푸치노",
          "밀크쉐이크",
          "허브티",
        ],
        datasets: [
          {
            data: [12, 19, 3, 5, 2, 3],
            backgroundColor: [
              /* 파스텔 노란색 */
              "rgba(64, 224, 208, 0.6)",

              /* 파스텔 녹색 */
              "rgba(162, 210, 182, 1)",

              /* 파스텔 파란색 */
              "rgba(174, 198, 207, 1)",

              /* 파스텔 핑크색 */
              "rgba(244, 194, 194, 1)",

              /* 파스텔 보라색 */
              "rgba(204, 174, 221, 1)",

              "rgba(244, 194, 194, 1)",
            ],
            borderWidth: 1,
          },
        ],
      },
      lineChartData: {
        labels: ["1월", "2월", "3월", "4월", "5월", "6월"],
        datasets: [
          {
            label: "월별 매출",
            data: [73, 78, 80, 85, 83, 84],
            fill: false,
            borderColor: "rgba(8, 92, 0, 1)",
            tension: 0.1,
          },
        ],
      },
      showNotification: false,
      notificationText: "",
    };
  },
  mounted() {
    this.createBarChart();
    this.createPieChart();
    this.createLineChart();
    this.fetchNotices();
    this.checkDiscardedProducts();
  },
  methods: {
    checkDiscardedProducts() {
      const userStore = useUserStore(); // Pinia 스토어 사용
      if (
        userStore.discardedProduct &&
        userStore.discardedProduct.length > 0
      ) {
        // 폐기 대상 상품이 있으면 알림 표시
        this.showNotification = true;
        this.notificationText = `폐기 대상 상품이 ${userStore.discardedProduct.length}개 있습니다. 관리 페이지에서 확인해주세요.`;
      }
    },
    hideNotification() {
      this.showNotification = false;
    },
    createChart(chartRef, chartType, chartData, chartOptions) {
      const ctx = this.$refs[chartRef].getContext("2d");
      new Chart(ctx, {
        type: chartType,
        data: chartData,
        options: chartOptions,
      });
    },
    createBarChart() {
      const barChartOptions = {
        plugins: {
          legend: {
            display: false, // 레전드를 표시하도록 설정
          },
        },
        scales: {
          x: {
            grid: {
              display: false,
            },
          },
          y: {
            grid: {
              display: false,
            },
          },
        },
      };
      this.createChart("MyChart", "bar", this.barChartData, barChartOptions);
    },
    createPieChart() {
      const pieChartOptions = {
        plugins: {
          legend: {
            display: false, // 레전드를 표시하도록 설정
          },
        },
      };
      this.createChart(
        "myPieChart",
        "doughnut",
        this.pieChartData,
        pieChartOptions
      );
    },
    createLineChart() {
      const lineChartOptions = {
        plugins: {
          legend: {
            display: false, // 레전드를 표시하도록 설정
          },
        },
        scales: {
          x: {
            grid: {
              display: false,
            },
          },
          y: {
            grid: {
              display: false,
            },
          },
        },
      };
      this.createChart(
        "myLineChart",
        "line",
        this.lineChartData,
        lineChartOptions
      );
    },
    async fetchNotices() {
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.get(
          "http://121.140.125.34:11113/api/notice/list",
          {
            params: {
              page: 1,
              size: 10,
            },
            headers: {
              AccessToken: accessToken,
            },
          }
        );
        this.notices = response.data.result;
      } catch (error) {
        console.error(
          "공지사항 목록을 불러오는 중 오류가 발생했습니다.",
          error
        );
      }
    },
  },
  components: {
    Swiper,
    SwiperSlide,
  },
};
</script>

<style scoped>
.mySwiper {
  width: 100%;
  height: 300px;
  margin: 20px auto;
  border-radius: 10px;
}

.swiper-slide {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%; /* 슬라이드 높이를 스와이퍼 컨테이너 높이와 동일하게 설정 */
}

.swiper-slide img {
  width: 100%; /* 슬라이드의 너비에 맞추어 이미지 너비를 설정 */
  height: 100%; /* 슬라이드의 높이에 맞추어 이미지 높이를 설정 */
  object-fit: cover; /* 이미지가 슬라이드를 꽉 채우도록 설정, 이미지의 일부가 잘릴 수 있음 */
  /* object-fit: contain;를 사용하면 이미지 전체가 보이되, 슬라이드를 꽉 채우지 않을 수 있음 */
}
.main-container {
  max-width: 1000px; /* 원하는 최대 너비로 조정하세요 */
  margin: 0 auto; /* 중앙 정렬을 위해 사용 */
}
a {
  color: #00704a;
}
.notice-title {
  border-color: #00704a; /* This will set the color of .notice-title to red */
}
</style>
