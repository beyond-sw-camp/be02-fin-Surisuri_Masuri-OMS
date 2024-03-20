import { shallowMount } from '@vue/test-utils';
import axios from 'axios';
import ResetLoginView from '@/views/ResetLoginView.vue';

jest.mock('axios', () => ({
  get: jest.fn()
}));

describe('ResetLoginView', () => {
  it('이름과 핸드폰 번호를 입력하고 이메일 찾기 버튼을 클릭했을 때 동작하는지 확인', async () => {
    const wrapper = shallowMount(ResetLoginView);

    const responseData = { result: { userEmail: 'test@example.com' } };
    axios.get.mockResolvedValue({ data: responseData });

    await wrapper.setData({ userName: '김광두', userPhoneNo: '010-1234-5678' });

    await wrapper.find('form').trigger('submit.prevent');

    expect(axios.get).toHaveBeenCalledWith('http://localhost:8080/user/findEmail', {
      params: {
        userName: '김광두',
        userPhoneNo: '010-1234-5678'
      }
    });

    // API 요청이 성공적으로 이루어졌을 때 찾은 아이디가 화면에 나타나는지 확인
    await wrapper.vm.$nextTick();
    expect(wrapper.vm.foundId).toBe('test@example.com');
  });

  it('오류 발생 시 console.error 호출되는지 확인', async () => {
    const wrapper = shallowMount(ResetLoginView);

    console.error = jest.fn();

    axios.get.mockRejectedValueOnce(new Error('테스트 오류'));

    await wrapper.setData({ userName: '김광두', userPhoneNo: '010-1234-5678' });

    // 이메일 찾기 버튼 클릭
    await wrapper.find('form').trigger('submit.prevent');

    // 오류가 발생하여 console.error가 호출되었는지 확인
    expect(console.error).toHaveBeenCalled();
  });
});
