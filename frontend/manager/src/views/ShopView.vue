<template>
    <div>
      <div class="container mt-5">
        <div class="row">
          <div class="col">
            <input type="text" v-model="searchQuery" class="form-control search-input" placeholder="가맹점 검색">
          </div>
          <div class="col-auto">
            <button class="btn btn-primary" @click="fetchShops">검색</button>
          </div>
        </div>
        <table class="table mt-3">
          <thead>
            <tr>
              <th scope="col">가맹점 이름</th>
              <th scope="col">주소</th>
              <th scope="col">UUID</th>
              <th scope="col">전화번호</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="shop in filteredShops" :key="shop.storeUuid">
              <td>{{ shop.storeName }}</td>
              <td>{{ shop.storeAddr }}</td>
              <td>{{ shop.storeUuid }}</td>
              <td>{{ shop.userPhoneNo }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </template>
<script>
import axios from 'axios';

export default {
    name: "ShopView",
    data() {
        return {
            searchQuery: '',
            shops: [], // 빈 배열로 초기화
            pagination: {
                page: 1,
                size: 10,
            },
        }
    },
    computed: {
        filteredShops() {
            // shops가 정의되지 않은 상태를 방지하기 위해 초기화된 배열 사용
            return this.shops ? this.shops.filter(shop => shop.storeName.toLowerCase().includes(this.searchQuery.toLowerCase())) : [];
        }
    },
    methods: {
        async fetchShops() {
            try {
                const token = sessionStorage.getItem('token');
                const response = await axios.get('http://192.168.0.44/api/store/list', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    },
                    params: {
                        ...this.pagination,
                        searchQuery: this.searchQuery
                    }
                });
                // 서버 응답에 따라 .data 구조 조정 필요
                this.shops = response.data.result || []; // 응답이 없을 경우 빈 배열 할당
                console.log("가맹점 조회 응답:", response.data);
            } catch (error) {
                console.error("가맹점 조회 오류:", error);
                this.shops = []; // 오류 발생 시 shops를 빈 배열로 초기화
            }
        }
    },
    watch: {
        searchQuery() {
            // 검색어가 변경될 때마다 가맹점 다시 불러오기
            this.fetchShops();
        }
    },
    created() {
        this.fetchShops(); // 컴포넌트 생성 시 가맹점 데이터 불러오기
    }
}
</script>


<style scoped>

/* 여기에 추가 스타일을 넣으세요. */
</style>
