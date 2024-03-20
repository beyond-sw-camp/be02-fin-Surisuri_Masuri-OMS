import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router';
import './assets/fonts.css';
import './assets/global.css';

// Pinia 먼저 생성
const pinia = createPinia();

const app = createApp(App);
app.use(pinia);
app.use(router).mount('#app'); // pinia를 초기화 하고 앱에 적용