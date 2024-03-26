import { shallowMount } from '@vue/test-utils';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import ProductView from '@/views/ProductView'; // 경로는 실제 컴포넌트의 위치에 맞게 조정해야 합니다.
import CartButtonComponent from '@/components/CartButtonComponent.vue'; // 필요한 경우

// axios 호출을 모의하기 위한 Mock Adapter 생성
const mock = new MockAdapter(axios);

describe('ProductView.vue', () => {
  beforeEach(() => {
    mock.reset(); // 각 테스트 이전에 mock을 리셋
  });

  it('fetchProducts 메소드는 제품 목록을 가져온다', async () => {
    const products = [{ productName: 'Test Product', price: 100, stockQuantity: 10 }]; // 예상되는 응답 데이터
    mock.onGet("http://localhost:8080/product/list").reply(200, { result: products });

    const wrapper = shallowMount(ProductView);
    await wrapper.vm.fetchProducts(); // fetchProducts 호출

    expect(wrapper.vm.products).toEqual(products); // products 데이터가 예상대로 설정되었는지 확인
  });

  it('addToCart 메소드는 장바구니에 제품을 추가한다', async () => {
    const response = { message: '장바구니에 추가됨' };
    mock.onPost("http://localhost:8080/cart/addcart").reply(200, response);

    const wrapper = shallowMount(ProductView);
    const product = { productIdx: 1, productName: 'Test Product', purchaseQuantity: 1 }; // 테스트용 제품 객체
    await wrapper.vm.addToCart(product); // addToCart 호출

    expect(wrapper.vm.cartItemCount).toBe(product.purchaseQuantity); // cartItemCount가 업데이트 되었는지 확인
  });

  // 다른 메소드나 컴포넌트 동작에 대한 추가 테스트를 여기에 작성할 수 있습니다.
});
