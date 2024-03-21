// Import dependencies
import { shallowMount } from '@vue/test-utils';
import StockEditView from '@/views/StockEditView.vue';
import axios from 'axios';

// Mock axios globally
jest.mock('axios', () => ({
  get: jest.fn()
}));

describe('MyComponent', () => {
  let wrapper;

  beforeEach(() => {
    // Reset the mock before each test
    axios.get.mockReset();

    // Mount the component before each test
    wrapper = shallowMount(StockEditView, {
      // Provide any required props, mocks or local Vue options here
    });

    // Mock sessionStorage
    Storage.prototype.getItem = jest.fn(() => 'mockedToken');
  });

  it('fetchStockList - should fetch stock list successfully', async () => {
    // Mock axios response
    const mockResponse = {
      data: {
        result: [
          { storeStockDto: { productName: 'Product 1' }, stockQuantitiy: 10, storeStockIdx: 1 },
          { storeStockDto: { productName: 'Product 2' }, stockQuantitiy: 20, storeStockIdx: 2 }
        ]
      }
    };
    axios.get.mockResolvedValue(mockResponse);

    // Call the method
    await wrapper.vm.fetchStockList();

    // Assertions
    expect(axios.get).toHaveBeenCalledWith(expect.any(String), {
      headers: { Authorization: 'Bearer mockedToken' },
      params: { page: wrapper.vm.page, size: wrapper.vm.size },
    });
    expect(wrapper.vm.stockList).toEqual(expect.arrayContaining([
      expect.objectContaining({ storeStockDto: expect.any(Object), stockQuantitiy: expect.any(Number) })
    ]));
    expect(wrapper.vm.stockList.length).toBe(2);
  });

  // Add more tests here for error handling and other scenarios
});
