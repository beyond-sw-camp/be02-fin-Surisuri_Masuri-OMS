<template>
    <div @click="goToCart" class="position-fixed bottom-0 end-0 m-3">
      <button type="button" class="btn btn-primary">
        장바구니
        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
          {{ cartItemCount }}
          <span class="visually-hidden">장바구니 아이템 수</span>
        </span>
      </button>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      cartItemCount: {
        type: Number,
        required: true
      }
    },
    methods: {
      goToCart() {
        this.$router.push('/cart'); // '/cart'는 장바구니 페이지의 라우트 경로
      }
    }
  }
  </script>
  
  <style scoped>
  </style>