import { mount } from '@vue/test-utils'
import OrderDetailView from '@/views/OrderDetailView.vue'
import axios from 'axios'

// axios 요청을 가로채기 위한 mock 설정
jest.mock('axios', () => ({
  get: jest.fn()
}))

// console.error를 mock
console.error = jest.fn()

describe('OrderDetailView', () => {
  let wrapper

  beforeEach(() => {
    // 초기화
    wrapper = mount(OrderDetailView)
  })

  afterEach(() => {
    // axios.get이 호출되었는지 확인하고 모든 모의 함수를 재설정
    jest.clearAllMocks()
  })

  it('renders the component', () => {
    // 컴포넌트가 렌더링되었는지 확인
    expect(wrapper.exists()).toBe(true)
  })

  it('fetches orders detail when mounted', async () => {
    // 예상되는 주문 상세 정보
    const ordersDetailResult = [
      { 
        createdDate: '2024-03-21T10:30:00',
        merchantUid: '123456',
        productDtoRes: {
          productName: 'Test Product',
          productQuantity: 2
        },
        totalPrice: 50,
        deliveryStatus: '배송 중'
      },
      // 다른 주문 정보들 추가
    ]

    // axios.get 모의 함수 설정
    axios.get.mockResolvedValue({ data: { result: ordersDetailResult } })

    // 컴포넌트가 마운트되고 데이터가 로드되는지 확인
    await wrapper.vm.$nextTick()

    // axios.get이 호출되었는지 확인
    expect(axios.get).toHaveBeenCalledWith('http://121.140.125.34:11113/api/orders/list', {
      params: {
        page: 1,
        size: 5
      },
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem('token')}`
      }
    })

    // 데이터가 올바르게 로드되었는지 확인
    expect(wrapper.vm.ordersDetailResult).toEqual(ordersDetailResult)
    expect(wrapper.vm.totalOrders).toBe(ordersDetailResult.length)

    const todayStr = new Date().toISOString().slice(0, 10)
    const todayOrders = ordersDetailResult.filter(order => order.createdDate.slice(0, 10) === todayStr).length
    expect(wrapper.vm.todayOrders).toBe(todayOrders)

    const shippingOrders = ordersDetailResult.filter(order => order.deliveryStatus === '배송 중').length
    expect(wrapper.vm.shippingOrders).toBe(shippingOrders)
  })

  it('handles errors during data fetching', async () => {
    // axios.get 모의 함수 설정
    axios.get.mockRejectedValue(new Error('Network error'))

    // 컴포넌트가 마운트되고 데이터를 가져오는 동안 오류가 발생하는지 확인
    await wrapper.vm.$nextTick()

    // 오류가 발생하고 console.error가 호출되었는지 확인
    expect(console.error).toHaveBeenCalledWith('Error fetching orders detail:', new Error('Network error'))
  })
})
