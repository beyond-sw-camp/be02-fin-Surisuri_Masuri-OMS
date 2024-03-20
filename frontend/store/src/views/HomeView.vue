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
                    :key="notice.idx"
                  >
                    <th scope="row">{{ index + 1 }}</th>
                    <td>
                      <!-- 문의사항 제목에 router-link 적용하여 상세 페이지로 이동 -->
                      <router-link
                        :to="{
                          name: 'NoticeDetail',
                          query: {
                            idx: notice.idx,
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
       
          <swiper
            class="mySwiper"
            :options="{ navigation: { clickable: true } }"
          >
            <swiper-slide v-for="image in images" :key="image">
              <img :src="image" alt="사진 슬라이드" />
            </swiper-slide>
          </swiper>
          <div class="card-body p-4">
            <h5 class="card-title fw-semibold mb-4"></h5>
            <div class="table-responsive">
              <table class="table text-nowrap mb-0 align-middle"></table>
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
          "모카",
        ],
        datasets: [
          {
            label: "재고 수량",
            data: [18, 19, 15, 12, 17, 7, 14, 16, 9, 11, 13],
            backgroundColor: [
              "rgba(5, 61, 0, 1)", // 라이트 노란색
              "rgba(8, 92, 0, 1)", // 앰버
              "rgba(12, 148, 0, 1)", // 레몬 노란색
              "rgba(124, 173, 0, 1)", // 다크 레몬
              "rgba(51, 105, 27, 1)", // 오렌지 노란색
              "rgba(77, 182, 32, 1)", // 진한 오렌지 노란색
              "rgba(155, 211, 130, 1)", // 단풍 노란색
              "rgba(2, 44, 22, 1)", // 깊은 오렌지
              "rgba(24, 77, 15, 1)", // 단풍 오렌지
              "rgba(44, 141, 27, 1)", // 밝은 단풍 오렌지
              "rgba(88, 153, 77, 1)",
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
              "rgba(88, 153, 77, 1)", // 라이트 노란색
              "rgba(8, 92, 0, 1)", // 앰버
              "rgba(88, 153, 77, 1)", // 레몬 노란색
              "rgba(5, 61, 0, 1)", // 다크 레몬
              "rgba(5, 61, 0, 1)", // 깊은 오렌지
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
    };
  },
  mounted() {
    this.createBarChart();
    this.createPieChart();
    this.createLineChart();
    this.fetchNotices();
  },
  methods: {
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
        const response = await axios.get("http://192.168.0.162/notice/list", {
          params: {
            page: 1,
            size: 10,
          },
        });
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
  max-width: 1100px; /* 원하는 최대 너비로 조정하세요 */
  margin: 0 auto; /* 중앙 정렬을 위해 사용 */
}
a {
  color: #00704a;
}
.notice-title {
  border-color: #00704a; /* This will set the color of .notice-title to red */
}
</style>
