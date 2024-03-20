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
import com.example.Surisuri_Masuri.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(value = SpringExtension.class)
@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = {SecurityConfig.class, ProductController.class})
class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ManagerService managerService;

    @MockBean
    private ProductService productService;

    @Test
    void productController_create_success() throws Exception {
        String productName = "test";
        Integer price = 1000;

        given(productService.create(any(ProductCreateReq.class))).willReturn(BaseResponse
                .successResponse("요청 성공", null));

        ProductCreateReq req = new ProductCreateReq();
        req.setProductName(productName);
        req.setPrice(price);
        req.setIsItFood(false);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(req);
        System.out.println(content);
        mvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }


    @Test
    void productController_search_success() throws Exception {
        String productName = "test";
        Integer price = 1000;
        Date createAt = new Date();
        Date updateAt = new Date();
        ProductSearchRes res = ProductSearchRes.builder()
                .productName(productName)
                .price(price)
                .createdAt(createAt)
                .updatedAt(updateAt).build();


        given(productService.search(anyString())).willReturn(BaseResponse
                .successResponse("상품 검색 성공", res));

        mvc.perform(get("/product/search")
                        .param("productName", productName))
                .andExpect(status().isOk());
    }

    @Test
    void productController_list_success() throws Exception {
        Integer page = 1;
        Integer size = 5;
        String procutName = "test";
        Product product = Product.builder()
                .productName(procutName)
                .build();

        ProductListRes productListRes = ProductListRes.builder()
                .productName(product.getProductName())
                .build();

        List<ProductListRes> productListResList = new ArrayList<>();
        productListResList.add(productListRes);

        given(productService.list(anyInt(), anyInt())).willReturn(BaseResponse
                .successResponse("상품 리스트 검색 성공", productListResList));

        mvc.perform(get("/product/search")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk());
    }

    @Test
    void productController_update_success() throws Exception {
        Long productIdx = 1L;
        String productName = "test";
        Integer price = 1000;

        ProductUpdateReq productUpdateReq = ProductUpdateReq.builder()
                .idx(productIdx)
                .productName(productName)
                .price(price)
                .build();

        given(productService.update(any(ProductUpdateReq.class))).willReturn(BaseResponse
                .successResponse("상품 수정 성공", null));

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(productUpdateReq);
        System.out.println(content);
        mvc.perform(patch("/product/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void productController_delete_success() throws Exception {
        Long productIdx = 1L;

        given(productService.delete(anyLong())).willReturn(BaseResponse
                .successResponse("상품 삭제 성공", null));

        mvc.perform(delete("/product/delete")
                        .param("idx", String.valueOf(productIdx)))
                .andExpect(status().isOk());
    }
}