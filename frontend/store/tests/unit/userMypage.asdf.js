import { shallowMount } from '@vue/test-utils';
import axios from 'axios';
import UserProfileView from '@/views/UserProfileView.vue';

// axios 모의(mock) 설정
jest.mock('axios');

describe('UserProfileView', () => {
  it('updates user password on form submission', async () => {
    // 모의(mock)된 axios.patch를 설정
    axios.patch.mockResolvedValueOnce({ data: 'User info updated successfully' });

    // spyOn을 사용하여 window.alert를 감시된 함수로 만듦
    jest.spyOn(window, 'alert').mockImplementation(() => {});

    // shallowMount를 사용하여 UserProfileView 컴포넌트 래핑
    const wrapper = shallowMount(UserProfileView);

    // 패스워드 입력 필드에 값을 입력
    await wrapper.find('input[type="password"]').setValue('newPassword1!');

    // confirm 대화상자를 무조건 확인으로 설정
    window.confirm = jest.fn(() => true);

    // 폼 제출
    await wrapper.find('form').trigger('submit.prevent');

    // axios.patch가 호출되었는지 확인
    expect(axios.patch).toHaveBeenCalled();

    // 수정 성공 알림 확인
    expect(window.alert).toHaveBeenCalledWith('회원정보가 성공적으로 수정되었습니다.');
  });


  it('updates user phone number on form submission', async () => {
    // 모의(mock)된 axios.patch를 설정
    axios.patch.mockResolvedValueOnce({ data: 'User info updated successfully' });

    // shallowMount를 사용하여 UserProfileView 컴포넌트 래핑
    const wrapper = shallowMount(UserProfileView);

    // 핸드폰 번호 입력 필드에 값을 입력
    await wrapper.find('input[type="text"]').setValue('010-1234-1234');

    // confirm 대화상자를 무조건 확인으로 설정
    window.confirm = jest.fn(() => true);

    // 폼 제출
    await wrapper.find('form').trigger('submit.prevent');

    // axios.patch가 호출되었는지 확인
    expect(axios.patch).toHaveBeenCalled();

    // 수정 성공 알림 확인
    expect(window.alert).toHaveBeenCalledWith('회원정보가 성공적으로 수정되었습니다.');
  });

  it('updates store phone number on form submission', async () => {
    // 모의(mock)된 axios.patch를 설정
    axios.patch.mockResolvedValueOnce({ data: 'User info updated successfully' });

    // shallowMount를 사용하여 UserProfileView 컴포넌트 래핑
    const wrapper = shallowMount(UserProfileView);

    // 가맹점 번호 입력 필드에 값을 입력
    await wrapper.findAll('input[type="text"]').at(1).setValue('02-1234-1234');

    // confirm 대화상자를 무조건 확인으로 설정
    window.confirm = jest.fn(() => true);

    // 폼 제출
    await wrapper.find('form').trigger('submit.prevent');

    // axios.patch가 호출되었는지 확인
    expect(axios.patch).toHaveBeenCalled();

    // 수정 성공 알림 확인
    expect(window.alert).toHaveBeenCalledWith('회원정보가 성공적으로 수정되었습니다.');
  });

  it('updates store address on form submission', async () => {
    // 모의(mock)된 axios.patch를 설정
    axios.patch.mockResolvedValueOnce({ data: 'User info updated successfully' });

    // shallowMount를 사용하여 UserProfileView 컴포넌트 래핑
    const wrapper = shallowMount(UserProfileView);

    // 가맹점 주소 입력 필드에 값을 입력
    await wrapper.find('textarea').setValue('서울 서울구');

    // confirm 대화상자를 무조건 확인으로 설정
    window.confirm = jest.fn(() => true);

    // 폼 제출
    await wrapper.find('form').trigger('submit.prevent');

    // axios.patch가 호출되었는지 확인
    expect(axios.patch).toHaveBeenCalled();

    // 수정 성공 알림 확인
    expect(window.alert).toHaveBeenCalledWith('회원정보가 성공적으로 수정되었습니다.');
  });
});
