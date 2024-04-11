import { shallowMount } from '@vue/test-utils';
import ResetPasswordView from '@/views/ResetPasswordView.vue';
import { createRouter, createMemoryHistory } from 'vue-router';
import axios from 'axios';

jest.mock('axios');

describe('ResetPasswordView', () => {
  let wrapper;
  const mockRoute = {
    params: {
      idx: 'userIdx123', // 테스트용 임의의 사용자 인덱스
    },
  };

  beforeEach(() => {
    const router = createRouter({
      history: createMemoryHistory(),
      routes: [{ path: '/' }],
    });

    wrapper = shallowMount(ResetPasswordView, {
      global: {
        plugins: [router],
        mocks: {
          $route: mockRoute,
        },
      },
    });
  });

  afterEach(() => {
    wrapper.unmount();
  });

  it('비밀번호 재설정 요청 시 비밀번호가 서로 일치할 때 요청이 올바르게 수행되는지 확인', async () => {
    // 모의 응답 설정
    const responseData = { data: '비밀번호 재설정 성공' };
    axios.patch.mockResolvedValue(responseData);

    // 데이터 설정
    wrapper.setData({
      resetPasswordReq: {
        userPassword: 'newPassword123',
      },
      confirmPassword: 'newPassword123',
    });
  
    // 비밀번호 재설정 함수 호출
    await wrapper.vm.submitNewPassword();
  
    // 비밀번호 재설정 요청이 정상적으로 처리되었는지 확인
    expect(axios.patch).toHaveBeenCalledWith('http://121.140.125.34:11113/api/user/resetPassword/userIdx123', expect.any(FormData));
    expect(wrapper.vm.$data.alertMessage).toBe('비밀번호가 성공적으로 재설정되었습니다.');
  });
  
  it('비밀번호 재설정 요청 시 비밀번호가 서로 불일치할 때 알림이 올바르게 표시되는지 확인', async () => {
    // 데이터 설정 (비밀번호가 서로 일치하지 않도록 설정)
    wrapper.setData({
      resetPasswordReq: {
        userPassword: 'newPassword123',
      },
      confirmPassword: 'wrongPassword123',
    });
  
    // 비밀번호 재설정 함수 호출
    await wrapper.vm.submitNewPassword();
  
    // 비밀번호 불일치 알림이 정상적으로 표시되었는지 확인
    expect(wrapper.vm.$data.alertMessage).toBe('입력한 비밀번호가 서로 일치하지 않습니다. 다시 확인해 주세요.');
  });  
});
