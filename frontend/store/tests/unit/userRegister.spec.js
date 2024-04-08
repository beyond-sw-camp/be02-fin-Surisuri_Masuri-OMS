import { shallowMount } from '@vue/test-utils';
import { createRouter, createMemoryHistory } from 'vue-router';
import { setActivePinia, createPinia } from 'pinia';
import RegisterView from '@/views/RegisterView.vue';
import { ref } from 'vue';

describe('RegisterView', () => {
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

  it('폼이 제출될 때 createAccount 메서드가 호출되는지 확인', async () => {
    const wrapper = shallowMount(RegisterView, {
      global: {
        plugins: [router],
        provide: { pinia }
      },
      setup() {
        const userSignUpReq = {
          userName: '',
          userEmail: '',
          userPassword: '',
          userPhoneNo: '',
          storeUuid: '',
          storeAddr: '',
          storePhoneNo: ''
        };

        const createAccount = async () => {
        };

        return {
          userSignUpReq,
          confirmPassword: ref(''),
          createAccount
        };
      }
    });

    const createAccountMock = jest.spyOn(wrapper.vm, 'createAccount');

    // 회원 정보 입력
    wrapper.vm.userSignUpReq = {
      userName: '곽철식',
      userEmail: 'KCS@gmail.com',
      userPassword: '123456',
      userPhoneNo: '01012341234',
      storeUuid: '00000000-0000-0000-0000-000000000000',
      storeAddr: '서울특별시 강남구 삼성동',
      storePhoneNo: '0212341234'
    };

    // 회원 가입 버튼 호출
    await wrapper.find('form').trigger('submit.prevent');

    expect(createAccountMock).toHaveBeenCalled();
  });
});
