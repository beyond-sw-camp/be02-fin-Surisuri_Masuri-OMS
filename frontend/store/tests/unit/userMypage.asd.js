import { shallowMount } from '@vue/test-utils';
import OrderDetailView from '@/views/OrderDetailView.vue';
import axios from 'axios';

// axios 모의(mock) 설정
jest.mock('axios');

describe('OrderDetailView', () => {
  it('fetches orders detail and sets counters correctly on mount', async () => {
    // 모의(mock)된 axios.get를 설정
    const mockResponseData = {
      result: [
        { createdDate: '2024-03-21T10:30:00Z', merchantUid: '1234', productDtoRes: { productName: 'Coffee', productQuantity: 2 }, totalPrice: 5000, deliveryStatus: '배송 중' },
        { createdDate: '2024-03-21T11:30:00Z', merchantUid: '5678', productDtoRes: { productName: 'Tea', productQuantity: 1 }, totalPrice: 3000, deliveryStatus: '배송 완료' },
        // 다른 주문 정보들 추가 가능
      ]
    };
    axios.get.mockResolvedValueOnce({ data: mockResponseData });

    // shallowMount를 사용하여 OrderDetailView 컴포넌트 래핑
    const wrapper = shallowMount(OrderDetailView);

    // axios.get가 호출되었는지 확인
    expect(axios.get).toHaveBeenCalled();

    // axios.get 호출 시 전달된 URL과 인자 값이 기대한 대로인지 확인
    const expectedUrl = 'http://localhost:8080/orders/list';
    const expectedParams = {
      params: {
        page: 1,
        size: 5,
      },
      headers: {
        Authorization: 'Bearer mockToken', // 토큰 값은 여기서 실제로 쓰이지 않으므로 임의의 값으로 설정
      },
    };
    expect(axios.get.mock.calls[0][0]).toEqual(expectedUrl); // 첫 번째 호출의 URL 확인
    expect(axios.get.mock.calls[0][1]).toEqual(expectedParams); // 첫 번째 호출의 인자 값 확인

    // ordersDetailResult 배열이 mockResponseData.result와 같은지 확인
    expect(wrapper.vm.ordersDetailResult).toEqual(mockResponseData.result);

    // totalOrders 카운터가 올바르게 설정되었는지 확인
    expect(wrapper.vm.totalOrders).toBe(mockResponseData.result.length);

    // todayOrders 카운터가 올바르게 설정되었는지 확인
    const todayOrdersCount = mockResponseData.result.filter(order => order.createdDate.slice(0, 10) === '2024-03-21').length;
    expect(wrapper.vm.todayOrders).toBe(todayOrdersCount);

    // shippingOrders 카운터가 올바르게 설정되었는지 확인
    const shippingOrdersCount = mockResponseData.result.filter(order => order.deliveryStatus === '배송 중').length;
    expect(wrapper.vm.shippingOrders).toBe(shippingOrdersCount);
  });

  // 추가적인 테스트 케이스를 추가할 수 있습니다.
});
