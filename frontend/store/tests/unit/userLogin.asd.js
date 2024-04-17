import { shallowMount } from '@vue/test-utils';
import { createRouter, createMemoryHistory } from 'vue-router';
import { setActivePinia, createPinia } from 'pinia';
import LoginView from '@/views/LoginView.vue';

describe('LoginView', () => {
  beforeEach(() => {
    setActivePinia(createPinia())
  });

  const router = createRouter({
    history: createMemoryHistory('/'),
    routes: [
      { path: '/', redirect: '/home' },
      { path: '/home', component: { template: '<div>Home</div>' } }
    ]
  });

  // Pinia 초기화
  const pinia = createPinia();

  it('로그인 폼이 올바른 초기 데이터와 함께 렌더링 되는지 확인', async () => {
    const wrapper = shallowMount(LoginView, {
      global: {
        plugins: [router],
        provide: { pinia } // Pinia를 provide로 전달
      },
      data() {
        return {
          userEmail: '',
          userPassword: ''
        };
      }
    });

    await wrapper.vm.$nextTick();

    // 초기 데이터가 올바르게 렌더링되는지 확인
    expect(wrapper.vm.userEmail).toBe('');
    expect(wrapper.vm.userPassword).toBe('');
  });

  it('이메일,패스워드를 입력하고 로그인 버튼을 눌러서 로그인이 되는지 확인', async () => {
    const wrapper = shallowMount(LoginView, {
      global: {
        plugins: [router],
        provide: { pinia }
      },
      data() {
        return {
          userEmail: '',
          userPassword: ''
        };
      }
    });

    // loginSubmit 메서드를 spyOn하여 호출 여부 확인
    const loginSubmitMock = jest.spyOn(wrapper.vm, 'loginSubmit');

    // 이메일, 패스워드 입력
    await wrapper.setData({ userEmail: 'test@example.com', userPassword: 'password123' });

    // 로그인 버튼 호출
    await wrapper.find('form').trigger('submit.prevent');

    // loginSubmit 메서드가 호출되었는지 확인
    expect(loginSubmitMock).toHaveBeenCalled();
  });
});
