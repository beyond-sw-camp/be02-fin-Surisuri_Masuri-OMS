package Product;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.config.SecurityConfig;
import com.example.Surisuri_Masuri.member.Service.ManagerService;
import com.example.Surisuri_Masuri.member.Service.UserService;
import com.example.Surisuri_Masuri.product.controller.ProductController;
import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.model.dto.request.ProductCreateReq;
import com.example.Surisuri_Masuri.product.model.dto.request.ProductUpdateReq;
import com.example.Surisuri_Masuri.product.model.dto.response.ProductListRes;
import com.example.Surisuri_Masuri.product.model.dto.response.ProductSearchRes;
import com.example.Surisuri_Masuri.product.repository.ProductRepository;
import com.example.Surisuri_Masuri.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void productService_create_success() {
        //given
        Product test = Product.builder()
                .price(1000)
                .productName("test")
                .build();

        given(productRepository.save(any(Product.class))).willReturn(test);
        ProductCreateReq request = ProductCreateReq.builder()
                .productName("test")
                .price(1000)
                .build();

        //when
        BaseResponse response = productService.create(request);

        //then
        assertTrue(response.getIsSuccess());
        assertEquals(1000,  response.getCode());
        assertEquals(null,  response.getResult());
        assertEquals("요청 성공",  response.getMessage());
    }

}