import { shallowMount } from '@vue/test-utils';
import PasswordView from '@/views/PasswordView.vue';
import axios from 'axios';

// axios 모듈의 mock을 생성합니다.
jest.mock('axios');

describe('PasswordView', () => {
  let spyAlert;

  beforeEach(() => {
    // alert 함수를 spyOn하여 mock 함수로 대체합니다.
    spyAlert = jest.spyOn(window, 'alert').mockImplementation(() => {});
  });

  afterEach(() => {
    // 각 테스트가 끝날 때 spyOn된 alert 함수를 복구합니다.
    spyAlert.mockRestore();
  });

  it('비밀번호 재설정을 요청할 때 올바른 API 요청이 보내지는지 확인', async () => {
    // 테스트할 컴포넌트를 shallowMount를 사용하여 래핑합니다.
    const wrapper = shallowMount(PasswordView);
    
    // 사용자 이름과 이메일 입력란에 값을 설정합니다.
    await wrapper.setData({ userName: '테스트', userEmail: 'test@example.com' });

    // axios.post의 응답을 설정합니다.
    const responseData = { success: true };
    axios.post.mockResolvedValue({ data: responseData });

    // 비밀번호 재설정 버튼을 클릭합니다.
    await wrapper.find('form').trigger('submit.prevent');

    // axios.post가 올바른 URL과 함께 호출되었는지 확인합니다.
    expect(axios.post).toHaveBeenCalledWith(
      'http://localhost:8080/user/findPassword',
      { userName: '테스트', userEmail: 'test@example.com' },
      {
        headers: {
          'Content-Type': 'application/json',
        },
      }
    );

    // 비밀번호 재설정 요청이 성공했을 때 알림 메시지가 표시되는지 확인합니다.
    expect(window.alert).toHaveBeenCalledWith('비밀번호 재설정 링크가 이메일로 전송되었습니다. 메일을 확인해주세요.');
  });

  it('비밀번호 재설정 요청 시 서버 오류가 발생했을 때 처리가 올바르게 되는지 확인', async () => {
    // 테스트할 컴포넌트를 shallowMount를 사용하여 래핑합니다.
    const wrapper = shallowMount(PasswordView);
    
    // 사용자 이름과 이메일 입력란에 값을 설정합니다.
    await wrapper.setData({ userName: '테스트', userEmail: 'test@example.com' });

    // axios.post의 응답을 설정합니다.
    const errorMessage = 'Internal Server Error';
    const errorResponse = { response: { data: { message: errorMessage } } };
    axios.post.mockRejectedValue(errorResponse);

    // 비밀번호 재설정 버튼을 클릭합니다.
    await wrapper.find('form').trigger('submit.prevent');

    // 서버 오류가 발생했을 때 적절한 오류 메시지가 표시되는지 확인합니다.
    expect(window.alert).toHaveBeenCalledWith(`비밀번호 재설정 요청 중 오류가 발생했습니다.\n${errorMessage}`);
  });
});
