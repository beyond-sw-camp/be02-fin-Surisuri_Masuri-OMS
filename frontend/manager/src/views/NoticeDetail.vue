<template>
  <div class="container mt-5">
    <div class="card">
      <div class="card-body">
        <div v-if="!isEditing">
          <p class="card-text">공지 번호: {{ noticeIdx }}</p>
          <p class="card-text">제목: {{ title }}</p>
          <p class="card-text">내용: {{ content }}</p>
        </div>
        <div v-else>
          <input
            v-model="editData.title"
            class="form-control mb-2"
            placeholder="제목"
          />
          <textarea
            v-model="editData.content"
            class="form-control mb-2"
            placeholder="내용"
          ></textarea>
        </div>
        <!-- 수정/저장 버튼 -->
        <button
          v-if="!isEditing"
          class="btn btn-primary mt-3"
          @click="editPost"
        >
          수정하기
        </button>
        <button v-else class="btn btn-success mt-3" @click="savePost">
          저장하기
        </button>
        <button class="btn btn-danger mt-3" @click="deleteNotice">
          삭제하기
        </button>
      </div>
    </div>
    <router-link to="/notice" class="btn btn-secondary mt-3"
      >목록으로 돌아가기</router-link
    >
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      isEditing: false,
      noticeIdx: this.$route.query.idx,
      title: this.$route.query.title,
      content: this.$route.query.content,
      editData: {
        title: this.$route.query.title,
        content: this.$route.query.content,
      },
    };
  },
  methods: {
    editPost() {
      this.isEditing = true;
      this.editData.title = this.title;
      this.editData.content = this.content;
    },
    savePost() {
      const updateData = {
        noticeIdx: this.noticeIdx,
        title: this.editData.title,
        content: this.editData.content,
        category: this.$route.query.category,
        status: true,
      };

      // sessionStorage에서 accessToken을 가져옵니다.
      const accessToken = sessionStorage.getItem("accessToken");

      axios
        .patch("http://121.140.125.34:11114/api/notice/update", updateData, {
          headers: {
            "Content-Type": "application/json",
            // accessToken을 헤더에 추가합니다.
            AccessToken: accessToken,
          },
        })
        .then((response) => {
          console.log("수정 응답:", response);
          alert("공지사항이 수정되었습니다.");
          this.$router.push("/notice");
        })
        .catch((error) => {
          console.error("공지사항 수정 중 오류 발생:", error);
          alert(
            "오류 발생: " +
              (error.response && error.response.data.message
                ? error.response.data.message
                : "공지사항 수정 중 오류가 발생했습니다.")
          );
        });

      this.isEditing = false;
    },
    async deleteNotice() {
      try {
        if (!this.noticeIdx) {
          alert("삭제할 공지사항이 선택되지 않았습니다.");
          return;
        }

        // sessionStorage에서 accessToken을 가져옵니다.
        const accessToken = sessionStorage.getItem("accessToken");

        const response = await axios.delete(
          `http://121.140.125.34:11114/api/notice/delete?noticeIdx=${this.noticeIdx}`,
          {
            headers: {
              // accessToken을 헤더에 추가합니다.
              AccessToken: accessToken,
            },
          }
        );
        console.log("공지사항 삭제 응답:", response.data);
        alert("공지사항이 성공적으로 삭제되었습니다.");

        this.noticeIdx = null;
        this.title = "";
        this.content = "";
        // 삭제 후 추가적으로 필요한 로직 처리
        this.$router.push("/notice");
      } catch (error) {
        console.error("공지사항 삭제 중 오류:", error);
        alert(
          "공지사항 삭제 중 오류가 발생했습니다: " +
            (error.response && error.response.data
              ? error.response.data.message
              : error.message)
        );
      }
    },
  },
};
</script>
