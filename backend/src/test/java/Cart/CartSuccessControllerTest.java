//package Cart;
//
//import com.example.Surisuri_Masuri.cart.controller.CartController;
//import com.example.Surisuri_Masuri.cart.model.dto.request.CartCreateReq;
//import com.example.Surisuri_Masuri.cart.service.CartService;
//import com.example.Surisuri_Masuri.common.BaseResponse;
//import com.example.Surisuri_Masuri.member.Model.Entity.User;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.junit.Assert.assertEquals;
//@RunWith(MockitoJUnitRunner.class)
//public class CartSuccessControllerTest {
//    @Mock
//    private CartService cartService;
//    @InjectMocks
//    private CartController cartController;
//    private User mockUser;
//    private CartCreateReq mockCartCreateReq;
//    @Before
//    public void setUp() {
//        mockUser = new User(); // 필요한 사용자 객체 생성
//        mockCartCreateReq = CartCreateReq.builder()
//                .productIdx(1L)
//                .productQuantity(2)
//                .build(); // 필요한 장바구니 생성 요청 객체 생성
//    }
//    @Test
//    public void testAddCart() {
//        BaseResponse<String> successResponse = BaseResponse.successResponse("Success", "Some result");
//        when(cartService.addCart(any(User.class), any(CartCreateReq.class)))
//                .thenReturn(successResponse);
//        ResponseEntity<BaseResponse<String>> responseEntity = cartController.addCart(mockUser, mockCartCreateReq);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(successResponse, responseEntity.getBody());
//    }
//    @Test
//    public void testList() {
//        BaseResponse<String> successResponse = BaseResponse.successResponse("Success", "Some result");
//        when(cartService.list(any(User.class), any(Integer.class), any(Integer.class)))
//                .thenReturn(successResponse);
//        ResponseEntity<BaseResponse<String>> responseEntity = cartController.list(mockUser, 1, 10);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(successResponse, responseEntity.getBody());
//    }
//    @Test
//    public void testDelete() {
//        BaseResponse<String> successResponse = BaseResponse.successResponse("Success", "Some result");
//        when(cartService.delete(any(User.class), any(Long.class), any(String.class)))
//                .thenReturn(successResponse);
//        ResponseEntity<BaseResponse<String>> responseEntity = cartController.delete(mockUser, 1L, "productName");
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(successResponse, responseEntity.getBody());
//    }
//}