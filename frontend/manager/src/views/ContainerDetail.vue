<template>
  <div class="container-fluid px-4">
    <div class="row">
      <div class="col">
        <div class="card">
          <div class="card-header">상품 목록</div>
          <div class="card-body">
            <table class="table">
              <thead>
                <tr>
                  <th scope="col">#</th>
                  <th scope="col">상품 이름</th>
                  <th scope="col">재고</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(container, index) in containerss" :key="container.containerIdx">
                  <th scope="row">{{ index + 1 }}</th>
                  <td>{{ container.productName }}</td>
                  <td>{{ container.productQuantity }}</td>
                </tr>
              </tbody>
            </table>
            <a @click="goBack" class="btn btn-primary btn-sm">목록으로 돌아가기</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      containerss: [],
    };
  },
  methods: {
    async fetchData(containerIdx) {
      try {
        // 두 번째 템플릿으로 전달된 컨테이너의 idx 값을 사용하여 데이터를 받아옴
        const response = await axios.get(`http://192.168.0.44/api/container/singlestock?containerIdx=${containerIdx}`);
        this.containerss = response.data.result; // 받아온 데이터를 containerss에 저장
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    },
    goBack() {
      this.$router.go(-1);
    },
  },
  mounted() {
    // 두 번째 템플릿이 마운트되면서 컨테이너의 idx 값을 받아와서 데이터를 불러옴
    const containerIdx = this.$route.params.containerIdx;
    this.fetchData(containerIdx);
  },
};
</script>