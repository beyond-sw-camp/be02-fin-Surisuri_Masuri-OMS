package Container;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.container.controller.ContainerController;
import com.example.Surisuri_Masuri.container.model.request.ContainerCreateProductReq;
import com.example.Surisuri_Masuri.container.model.request.PostCreateContainerReq;
import com.example.Surisuri_Masuri.container.model.request.ContainerStockDto;
import com.example.Surisuri_Masuri.container.service.ContainerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContainerSuccessControllerTest {

    @Mock
    private ContainerService containerService;

    @InjectMocks
    private ContainerController containerController;

    @Test
    void testCreateContainer() {
        // Given
        PostCreateContainerReq request = new PostCreateContainerReq();
        // Set request parameters...

        // Mock service response
        BaseResponse response = BaseResponse.successResponse("창고 등록 성공", null);
        when(containerService.create(any(PostCreateContainerReq.class))).thenReturn(response);

        // When
        ResponseEntity result = containerController.create(request);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(containerService, times(1)).create(request);
    }

    @Test
    void testCreateContainerProduct() {
        // Given
        ContainerCreateProductReq request = new ContainerCreateProductReq();
        // Set request parameters...

        // Mock service response
        BaseResponse response = BaseResponse.successResponse("창고 상품 등록 성공", null);
        when(containerService.createContainerProduct(any(ContainerCreateProductReq.class))).thenReturn(response);

        // When
        ResponseEntity result = containerController.createContainerProduct(request);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(containerService, times(1)).createContainerProduct(request);
    }

    @Test
    void testListContainers() {
        // Given
        Integer page = 1;
        Integer size = 10;

        // Mock service response
        BaseResponse response = BaseResponse.successResponse("창고 목록 조회 성공", Collections.emptyList());
        when(containerService.list(page, size)).thenReturn(response);

        // When
        ResponseEntity result = containerController.list(page, size);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(containerService, times(1)).list(page, size);
    }

//    @Test
//    void testSingleStockProduct() {
//        // Given
//        Integer idx = 1;
//
//        // Mock service response
//        BaseResponse response = BaseResponse.successResponse("창고 정보 조회 성공", Collections.emptyList());
//        when(containerService.singleStockProduct(idx)).thenReturn(response);
//
//        // When
//        ResponseEntity result = containerController.singleStockProduct(idx);
//
//        // Then
//        assertEquals(HttpStatus.OK, result.getStatusCode());
//        assertEquals(response, result.getBody());
//        verify(containerService, times(1)).singleStockProduct(idx);
//    }
}
