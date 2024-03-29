<template>
  <div class="container mt-4">
    <h1 class="mb-4">창고 목록</h1>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">창고 이름</th>
          <th scope="col">주소</th>
          <th scope="col">복잡도</th>
          <th scope="col">관리자</th>
          <th scope="col">연락처</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="(container, index) in containers"
          :key="container.containerIdx"
          @click="goToContainerDetail(container)"
        >
          <th scope="row">{{ index + 1 }}</th>
          <td style="cursor: pointer">{{ container.containerName }}</td>
          <td>{{ container.containerAddr }}</td>
          <td>{{ container.containerComplexity }}</td>
          <td>{{ container.containerManager }}</td>
          <td>{{ container.containerPhoneNo }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      containers: [], // 창고 목록을 저장할 배열
      pagination: {
        // 페이지네이션 정보를 저장할 객체
        page: 1, // 기본 페이지 번호
        size: 10, // 기본 페이지당 항목 수
      },
    };
  },
  created() {
    this.fetchContainers(); // 컴포넌트 생성 시 창고 목록을 불러오도록 설정
  },
  methods: {
    async fetchContainers() {
      try {
        const response = await axios.get("http://121.140.125.34:11114/api/container/list", {
          params: this.pagination,
        });
        // 성공적으로 데이터를 받아온 경우, 받아온 데이터의 구조와 내용을 콘솔에 출력
        console.log("받아온 데이터 객체 정보:", response.data);
        this.containers = response.data.result; // 'data.result'로 수정
      } catch (error) {
        if (!error.response) {
          console.error("네트워크 오류가 발생했습니다. 서버에 접근할 수 없습니다.");
        } else if (error.response.status === 404) {
          console.error("찾을 수 없는 리소스입니다.");
        } else if (error.response.status === 500) {
          console.error("서버 내부 오류가 발생했습니다.");
        } else {
          console.error("오류가 발생했습니다: ", error.message);
        }
      }
    },
    goToContainerDetail(container) {
      // 두 번째 템플릿으로 라우팅하면서 컨테이너의 idx 값을 전달
      this.$router.push({ name: "ContainerDetail", params: { containerIdx: container.containerIdx } });
    },
  },
};
</script>
