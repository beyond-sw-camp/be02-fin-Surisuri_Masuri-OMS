<template>
  <div class="container py-5">
    <!-- 흰색 카드로 감싸기 -->
    <div class="card">
      <div class="card-body">
        <form
          @submit.prevent="submitQuestion"
          class="needs-validation"
          novalidate
        >
          <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input
              id="title"
              v-model="question.title"
              type="text"
              class="form-control"
              required
            />
            <div class="invalid-feedback">제목을 입력해주세요.</div>
          </div>
          <div class="mb-3">
            <label for="content" class="form-label">내용</label>
            <textarea
              id="content"
              v-model="question.content"
              class="form-control"
              rows="3"
              required
            ></textarea>
            <div class="invalid-feedback">내용을 입력해주세요.</div>
          </div>
          <div class="mb-3">
            <label for="category" class="form-label">카테고리</label>
            <select
              id="category"
              v-model="question.category"
              class="form-select"
              required
            >
              <option disabled value="">카테고리를 선택하세요</option>
              <option>제품문의</option>
              <option>일반문의</option>
              <option>배송문의</option>
            </select>
            <div class="invalid-feedback">카테고리를 선택해주세요.</div>
          </div>
          <button type="submit" class="btn btn-primary">문의사항 제출</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import swal from "sweetalert";
export default {
  data() {
    return {
      question: {
        title: "",
        content: "",
        category: '',
        status: false,
      },
    };
  },
  methods: {
    async submitQuestion() {
      try {
        const accessToken = sessionStorage.getItem("accessToken");
        if (!accessToken) {
          console.error("토큰이 없습니다.");
          return;
        }

        await axios.post(
          "http://121.140.125.34:11113/api/question/create",
          this.question,
          {
            headers: {
              "Content-Type": "application/json",
              AccessToken: accessToken,
            },
          }
        );

        swal("문의사항이 성공적으로 제출되었습니다.");
        // 문의사항 제출 후 /inquiry 페이지로 리다이렉트
        this.$router.push("/question");
      } catch (error) {
        console.error(`문의사항 제출 실패: ${error}`);
        swal("문의사항 제출에 실패했습니다. 다시 시도해주세요.");
      }
    },
  },
};
</script>
