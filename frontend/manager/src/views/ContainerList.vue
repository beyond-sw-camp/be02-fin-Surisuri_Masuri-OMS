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
    <nav aria-label="Page navigation">
      <ul class="pagination justify-content-end">
        <li class="page-item">
          <a
            class="page-link"
            href="#"
            aria-label="Previous"
            @click.prevent="prevRange"
          >
            <span aria-hidden="true">&laquo;</span>
          </a>
        </li>
        <li
          class="page-item"
          v-for="page in pageRange"
          :key="page"
          :class="{ active: page === currentPage }"
        >
          <a
            class="page-link"
            href="#"
            @click.prevent="fetchContainers(page)"
            >{{ page }}</a
          >
        </li>
        <li class="page-item">
          <a
            class="page-link"
            href="#"
            aria-label="Next"
            @click.prevent="nextRange"
          >
            <span aria-hidden="true">&raquo;</span>
          </a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      containers: [], // 창고 목록을 저장할 배열
      currentPage: 1,
      totalPages: 50, // 실제 서버로부터 받아온 총 페이지 수입니다.
      pageRangeSize: 5, // 한 번에 보여줄 페이지 수입니다.
    };
  },
  computed: {
    pageRange() {
      const startPage =
        Math.floor((this.currentPage - 1) / this.pageRangeSize) *
          this.pageRangeSize +
        1;
      const endPage = Math.min(
        startPage + this.pageRangeSize - 1,
        this.totalPages
      );
      return Array.from(
        { length: endPage - startPage + 1 },
        (_, i) => startPage + i
      );
    },
  },
  created() {
    this.fetchContainers(this.currentPage); // 컴포넌트 생성 시 창고 목록을 불러오도록 설정
  },
  methods: {
    async fetchContainers(page) {
      this.currentPage = page;
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        const response = await axios.get(
          "http://121.140.125.34:11114/api/container/list",
          {
            params: {
              page: this.currentPage,
              size: 5,
            },
            headers: {
            AccessToken: accessToken,
          },
          }
        );
        this.containers = response.data.result; // 'data.result'로 수정
      } catch (error) {
        if (!error.response) {
          console.error(
            "네트워크 오류가 발생했습니다. 서버에 접근할 수 없습니다."
          );
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
      this.$router.push({
        name: "ContainerDetail",
        params: { containerIdx: container.containerIdx },
      });
    },
    nextRange() {
      const maxPage = Math.ceil(this.currentPage / this.pageRangeSize) * this.pageRangeSize;
      if (maxPage < this.totalPages) {
        this.fetchContainers(maxPage + 1);
      }
    },
    prevRange() {
      const startPage = Math.floor((this.currentPage - 1) / this.pageRangeSize) * this.pageRangeSize;
      if (startPage >= this.pageRangeSize) {
        this.fetchContainers(startPage - this.pageRangeSize + 1);
      }
    },
  },
};
</script>
