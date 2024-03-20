package Cart;

import com.example.Surisuri_Masuri.cart.controller.CartController;
import com.example.Surisuri_Masuri.cart.model.dto.request.CartCreateReq;
import com.example.Surisuri_Masuri.cart.service.CartService;
import com.example.Surisuri_Masuri.exception.EntityException.CartException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class CartControllerFailureTest {
    @Mock
    private CartService cartService;
    @InjectMocks
    private CartController cartController;
    private User mockUser;
    private CartCreateReq mockCartCreateReq;
    @Before
    public void setUp() {
        mockUser = new User(); // 필요한 사용자 객체 생성
        mockCartCreateReq = CartCreateReq.builder()
                .productIdx(1L)
                .productQuantity(2)
                .build(); // 필요한 장바구니 생성 요청 객체 생성
    }
    @Test(expected = CartException.class)
    public void testAddCart_CartCreate_002() {
        when(cartService.addCart(any(User.class), any(CartCreateReq.class)))
                .thenThrow(new CartException(ErrorCode.CartCreate_002, "상품 정보가 존재하지 않습니다."));
        cartController.addCart(mockUser, mockCartCreateReq);
    }
    @Test(expected = CartException.class)
    public void testAddCart_CartCreate_003() {
        when(cartService.addCart(any(User.class), any(CartCreateReq.class)))
                .thenThrow(new CartException(ErrorCode.CartCreate_003, "상품을 카트에 담는 것에 실패했습니다."));
        cartController.addCart(mockUser, mockCartCreateReq);
    }
    @Test(expected = CartException.class)
    public void testDeleteCart_CartDelete_002() {
        when(cartService.delete(any(User.class), any(Long.class), any(String.class)))
                .thenThrow(new CartException(ErrorCode.CartDelete_002, "카트를 삭제하는 것에 실패했습니다."));
        cartController.delete(mockUser, 1L, "productName");
    }
    @Test(expected = CartException.class)
    public void testDeleteCart_CartDelete_003() {
        when(cartService.delete(any(User.class), any(Long.class), any(String.class)))
                .thenThrow(new CartException(ErrorCode.CartDelete_003, "카트 정보가 존재하지 않습니다."));
        cartController.delete(mockUser, 1L, "productName");
    }
}
