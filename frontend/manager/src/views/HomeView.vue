<template>
  <main class="main-container">
    <div class="container-fluid px-4">
      <!-- 첫 번째 줄: Success Card와 Area Chart -->
      <div class="row">
        <!-- 생략된 Success Card와 Area Chart 코드... -->
      </div>
      <!-- 두 번째 줄: 카드 구조 변경 -->
      <div class="row">
        <!-- 첫 번째 카드 (크기 유지) -->
        <div class="col-xl-6 col-md-6">
          <div class="card mb-4">
            <div class="card-header">
              전국 가맹점 일일 매출 (원)
            </div>
            <div class="card-body">
              <canvas ref="myLineChart1"></canvas>
            </div>
          </div>
        </div>
        <!-- 두 번째 카드 (크기 유지) -->
        <div class="col-xl-6 col-md-6">
          <div class="card mb-4">
            <div class="card-header">
              전국 가맹점 월별 매출 (원)
            </div>
            <div class="card-body">
              <canvas ref="myLineChart2"></canvas>
            </div>
          </div>
        </div>
        <!-- 세 번째 카드 (크기 2배) -->
        
      </div>
      <!-- 새로운 행: 네 번째 카드 (전체 폭 차지) -->
      <div class="row">
        <div class="col-xl-6">
          <div class="card mb-4">
            <div class="card-header">
              창고 재고 현황
            </div>
            <div class="card-body2">
              <canvas ref="myPieChart" ></canvas>
            </div>
          </div>
        </div>
        <div class="col-xl-6">
          <div class="card mb-4">
            <div class="card-header">
              가맹점 별 발주액
            </div>
            <div class="card-body">
                                    <table id="datatablesSimple">
                                        <thead>
                                            <tr>
                                                <th>가맹점 이름</th>
                                                <th>담당자</th>
                                                <th>주문 항목</th>
                                                <th>주문 날짜</th>
                                                <th>금액</th>
                                                <th></th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>서울1호점</td>
                                                <td>마동팔</td>
                                                <td>원두</td>
                                                <td>2011/04/25</td>
                                                <td>5,000,000</td>
                                            </tr>
                                            <tr>
                                                <td>부산1호점</td>
                                                <td>곽팔추</td>
                                                <td>업소용 우유</td>
                                                <td>2011/07/25</td>
                                                <td>2,000,000</td>
                                            </tr>
                                            <tr>
                                                <td>제주1호점</td>
                                                <td>허태건</td>
                                                <td>플라스틱 빨대</td>
                                                <td>2011/07/25</td>
                                                <td>2,000,000</td>
                                            </tr>
                                            <tr>
                                                <td>강원1호점</td>
                                                <td>구동곡</td>
                                                <td>매장용 머그컵</td>
                                                <td>2011/07/25</td>
                                                <td>3,000,000</td>
                                            </tr>
                                            <tr>
                                                <td>독도1호점</td>
                                                <td>김두식</td>
                                                <td>휘핑크림</td>
                                                <td>2011/07/25</td>
                                                <td>2,000,000</td>
                                            </tr>
                                            
                                        
                                        </tbody>
                                    </table>
                                </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>



<script>
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

export default {
  data() {
    return {
      lineChartData1: {
        labels: ['월', '화', '수', '목', '금', '주말'],
        datasets: [{
          data: [3500000, 3600000, 4000000, 4500000, 4700000, 5000000],
          fill: false,
          borderColor: 'rgb(75, 192, 192)',
          tension: 0.1
        }]
      },
      pieChartData: {
        labels: ['원두', '플라스틱 빨대', '매장용 머그컵', '업소용 우유', '휘핑크림','시럽'],
        datasets: [{
          data: [100,70,30,20,60,80],
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
      lineChartData2: {
        labels: ['1월', '2월', '3월', '4월', '5월', '6월'],
        datasets: [{
          data: [100000000, 101100000, 101000000, 103000000, 102000000, 103000000],
          fill: false,
          borderColor: 'rgb(255, 99, 132)',
          tension: 0.1
        }]
      }
    };
  },
  mounted() {
  this.createLineChart('myLineChart1', this.lineChartData1);
  this.createLineChart('myLineChart2', this.lineChartData2);
  this.createPieChart('myPieChart', this.pieChartData);
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
    createLineChart(chartRef, chartData) {
  const lineChartOptions = {
    plugins: {
          legend: {
            display: false // 이 부분을 추가하여 레전드를 숨깁니다.
          }
        },
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
  this.createChart(chartRef, 'line', chartData, lineChartOptions);
},

createPieChart(chartRef, chartData) {
  const pieChartOptions = {
    aspectRatio: 1,
    plugins: {
      legend: {
        display: false // 이 부분을 추가하여 레전드를 숨깁니다.
      }
    }
  };
  this.createChart(chartRef, 'doughnut', chartData, pieChartOptions);
},
  }
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
  max-width: 1400px; /* 원하는 최대 너비로 조정하세요 */
  margin: 0 auto; /* 중앙 정렬을 위해 사용 */
}

.card-body canvas {
    width: 100% !important;
    height: auto !important; /* 높이를 자동으로 조정하거나 필요에 따라 고정값 지정 */
    aspect-ratio: 16 / 9; /* 캔버스의 비율을 유지하고 싶은 경우 사용 */
}

.card-body2 canvas {
    width: 50% !important;
    height: auto !important; /* 높이를 자동으로 조정하거나 필요에 따라 고정값 지정 */
    margin: 0 auto;
    aspect-ratio: 1 / 1; /* 캔버스의 비율을 유지하고 싶은 경우 사용 */
}

/* 테이블의 헤더와 셀에 패딩을 추가하여 공간을 늘립니다 */
#datatablesSimple th, 
#datatablesSimple td {
    padding: 12px 15px; /* 상하 패딩 12px, 좌우 패딩 15px */
}

/* 테이블 행 간의 간격을 늘립니다 */
#datatablesSimple tr {
    border-bottom: 1px solid #dddddd; /* 행 사이에 경계선을 추가하여 시각적 구분을 명확히 합니다 */
}

/* 테이블의 마지막 행의 하단 경계선을 제거합니다 */
#datatablesSimple tr:last-child {
    border-bottom: none;
}

/* 테이블 헤더의 배경색과 글씨색을 설정합니다 */
#datatablesSimple thead {
    background-color: #f8f9fa; /* 연한 회색 배경 */
    color: #333333; /* 어두운 글씨 */
}

/* 카드 헤더의 간격을 조정합니다 */


/* 카드 본문의 간격을 조정합니다 */
.card-body {
    padding: 20px; /* 모든 방향 패딩 20px */
}
</style>



