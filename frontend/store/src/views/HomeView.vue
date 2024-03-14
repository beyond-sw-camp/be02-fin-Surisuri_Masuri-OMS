<template><div class="container-fluid main-container">
  
  <!--  Row 1 -->
  <div class="row">
    <div class="col-lg-8 d-flex align-items-strech">
      <div class="card w-100" id = "chart-card" >
        <div class="card-body">
          <div class="d-sm-flex d-block align-items-center justify-content-between mb-9">
            <div class="mb-3 mb-sm-0">
              <h5 class="card-title fw-semibold">많이 팔린 상품</h5>
            </div>
            
          </div>
          <canvas ref="MyChart" ></canvas>
        </div>
      </div>
    </div>
    <div class="col-lg-4">
      <div class="row">
        <div class="col-lg-12">
          <!-- Yearly Breakup -->
          <div class="card overflow-hidden">
            <div class="card-body p-4" id = "chart-card1">
              
              <div class="row align-items-center">
                <div class="col-8" >
                  <h4 class="fw-semibold mb-3">상품 잔여 재고</h4>
                  <div class="d-flex align-items-center mb-3">
                    <span
                      class="me-1 rounded-circle bg-light-success round-20 d-flex align-items-center justify-content-center">
                      <i class="ti ti-arrow-up-left text-success"></i>
                    </span>
                    <p class="text-dark me-1 fs-3 mb-0">+9%</p>
                    <p class="fs-3 mb-0">last year</p>
                  </div>
                  <div class="d-flex align-items-center">
      
                  </div>
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
            <div class="card-body" id = "chart-card2">
              <div class="row alig n-items-start">
                <div class="col-8" id = "chart-card2">
                  <h5 class="card-title mb-9 fw-semibold"> 월별 매출 </h5>
                  <div class="d-flex align-items-center pb-1">
                    <span
                      class="me-2 rounded-circle bg-light-danger round-20 d-flex align-items-center justify-content-center">
                      <i class="ti ti-arrow-down-right text-danger"></i>
                    </span>
                    <p class="text-dark me-1 fs-3 mb-0">+9%</p>
                    <p class="fs-3 mb-0">last month</p>
                  </div>
                </div>
                <div class="col-4">
                  <div class="d-flex justify-content-end">
                    <div
                      class="text-white bg-secondary rounded-circle p-6 d-flex align-items-center justify-content-center">
                      <i class="ti ti-currency-dollar fs-6"></i>
                    </div>
                  </div>
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
            <h5 class="card-title fw-semibold">공지사항</h5>
          </div>
          
        </div>
      </div>
    </div>
    <div class="col-lg-8 d-flex align-items-stretch">
      <div class="card w-100">
        <swiper class="mySwiper" :options="{ navigation: { clickable: true } }">
              <swiper-slide v-for="image in images" :key="image">
                <img :src="image" alt="사진 슬라이드">
              </swiper-slide>
            </swiper>
        <div class="card-body p-4">
          <h5 class="card-title fw-semibold mb-4"></h5>
          <div class="table-responsive">
            <table class="table text-nowrap mb-0 align-middle">
              
              
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
  </div>
</template>

<script>
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);
import { Swiper, SwiperSlide } from "swiper/vue";
import "swiper/css";

export default {
  data() {
    return {
      images: [
        require("@/assets/image2.jpg"),
        require("@/assets/image.jpg"),
      ],
      barChartData: {
        labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
        datasets: [{
          label: '# of Votes',
          data: [12, 19, 3, 5, 2, 3],
          backgroundColor: [
            'rgba(173, 216, 230, 0.2)', // 하늘색
            'rgba(0, 191, 255, 0.2)',   // 깊은 하늘색
            'rgba(0, 0, 255, 0.2)',     // 파란색
            'rgba(0, 0, 139, 0.2)',     // 짙은 파란색
            'rgba(25, 25, 112, 0.2)'    // 남색
          ],
          borderColor: [
            'rgba(173, 216, 230, 1)', // 하늘색
            'rgba(0, 191, 255, 1)',   // 깊은 하늘색
            'rgba(0, 0, 255, 1)',     // 파란색
            'rgba(0, 0, 139, 1)',     // 짙은 파란색
            'rgba(25, 25, 112, 1)'    // 남색
          ],
          borderWidth: 1
        }]
      },
      pieChartData: {
        labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
        datasets: [{
          data: [12, 19, 3, 5, 2, 3],
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)'
          ]
        }]
      },
      lineChartData: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June'],
        datasets: [{
          label: 'Monthly Sales',
          data: [65, 59, 80, 81, 56, 55],
          fill: false,
          borderColor: 'rgb(75, 192, 192)',
          tension: 0.1
        }]
      }
    };
  },
  mounted() {
    this.createBarChart();
    this.createPieChart();
    this.createLineChart();
  },
  methods: {
    createChart(chartRef, chartType, chartData, chartOptions) {
      const ctx = this.$refs[chartRef].getContext('2d');
      new Chart(ctx, {
        type: chartType,
        data: chartData,
        options: chartOptions,
      });
    },
    createBarChart() {
      const barChartOptions = {
        scales: {
          x: {
            grid: {
              display: false
            }
          },
          y: {
            grid: {
              display: false
            }
          }
        }
      };
      this.createChart('MyChart', 'bar', this.barChartData, barChartOptions);
    },
    createPieChart() {
      const pieChartOptions = {
        plugins: {
          legend: {
            display: false // 이 부분을 추가하여 레전드를 숨깁니다.
          }
        }
      };
      this.createChart('myPieChart', 'doughnut', this.pieChartData, pieChartOptions);
    },
    createLineChart() {
      const lineChartOptions = {
        scales: {
          x: {
            grid: {
              display: false
            }
          },
          y: {
            grid: {
              display: false
            }
          }
        }
      };
      this.createChart('myLineChart', 'line', this.lineChartData, lineChartOptions);
    }
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
  max-width: 1300px; /* 원하는 최대 너비로 조정하세요 */
  margin: 0 auto; /* 중앙 정렬을 위해 사용 */
}

</style>

