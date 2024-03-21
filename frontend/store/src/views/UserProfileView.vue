<template>
  <div class="container my-5">
    <div class="row">
      <div class="col-md-4">
        <!-- Profile Sidebar -->
        <div class="card">
          <div class="card-body text-center">
            <h3>GIGA COFFEE</h3>
            <p>Store Manager</p>
            <hr>
            <p><strong>86</strong> Stores</p>
            <p><strong>40</strong> Menu</p>
            <p><strong>4.5K</strong> Staff</p>
            <hr>
          </div>
        </div>
      </div>
      <div class="col-md-8">
        <div class="card">
          <div class="card-header">
            <h4>회원정보 수정</h4>
          </div>
          <div class="card-body">
            <form @submit.prevent="submitUserUpdate">
              <div class="row mb-3">
                <div class="col">
                  <input v-model="userUpdateReq.userPassword" type="password" class="form-control" placeholder="Password" aria-label="First name">
                </div>
              </div>
              <div class="mb-3">
                <input v-model="userUpdateReq.userPhoneNo" type="text" class="form-control" placeholder="핸드폰 번호" aria-label="Email address">
              </div>
              <div class="mb-3">
                <input v-model="userUpdateReq.storePhoneNo" type="text" class="form-control" placeholder="가맹점 번호" aria-label="password">
              </div>
              <div class="mb-3">
                <textarea v-model="userUpdateReq.storeAddr" class="form-control" placeholder="가맹점 주소" aria-label="Address"></textarea>
              </div>
              <button type="submit" class="btn btn-primary">저장</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

const baseURL = 'http://localhost:8080'

export default {
  data() {
    return {
      userUpdateReq: {
        userPassword: '',
        userPhoneNo: '',
        storePhoneNo: '',
        storeAddr: '',
      },
    };
  },
  methods: {
    async submitUserUpdate() {
      // confirm 대화상자를 표시하여 사용자의 선택을 받습니다.
      const isConfirmed = confirm("정말 수정하시겠습니까?");
      
      // 사용자가 '확인'을 누른 경우에만 수정 요청을 진행합니다.
      if (isConfirmed) {
        try {
          const token = sessionStorage.getItem('token'); // 세션 스토리지에서 토큰 값 가져오기
          const response = await axios.patch( baseURL + `/user/update`, this.userUpdateReq, {
            headers: {
              Authorization: `Bearer ${token}`, // 토큰 값 헤더에 포함
              "Content-Type": "application/json",
            },
          });
          
          // 수정 성공 알림
          alert("회원정보가 성공적으로 수정되었습니다.");
          console.log(response.data);
          
          // 여기에서 추가적으로 페이지를 리디렉션하거나 다른 UI 업데이트를 수행할 수 있습니다.
        } catch (error) {
          // 수정 실패 알림
          alert("회원정보 수정 요청 실패.");
          console.error('회원정보 수정 요청 실패:', error);
        }
      } else {
        // '취소'를 누른 경우, 아무것도 하지 않습니다.
        console.log("회원정보 수정이 취소되었습니다.");
      }
    },
  },
};
</script>
