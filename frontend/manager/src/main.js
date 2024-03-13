import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import $ from 'jquery'
import './assets/fonts.css';
import './assets/global.css';
window.$ = $
window.jQuery = $

const pinia = createPinia();
const app = createApp(App);
app.use(pinia);
app.use(router).mount('#app');
