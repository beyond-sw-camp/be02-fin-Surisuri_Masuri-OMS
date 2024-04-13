//package Container;
//
//import com.example.Surisuri_Masuri.common.BaseResponse;
//import com.example.Surisuri_Masuri.container.controller.ContainerController;
//import com.example.Surisuri_Masuri.container.model.request.ContainerCreateProductReq;
//import com.example.Surisuri_Masuri.container.model.request.PostCreateContainerReq;
//import com.example.Surisuri_Masuri.container.service.ContainerService;
//import com.example.Surisuri_Masuri.exception.EntityException.ContainerException;
//import com.example.Surisuri_Masuri.exception.EntityException.ContainerStockException;
//import com.example.Surisuri_Masuri.exception.ErrorCode;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ContainerControllerFailureTest {
//
//    @Mock
//    private ContainerService containerService;
//
//    @InjectMocks
//    private ContainerController containerController;
//
//    @Test
//    void testCreateContainer_DuplicateAddress() {
//        PostCreateContainerReq request = new PostCreateContainerReq();
//        when(containerService.create(any(PostCreateContainerReq.class))).thenThrow(new ContainerException(ErrorCode.ContainerCreate_003, "Container Addr [address] is duplicated."));
//        ContainerException exception = assertThrows(ContainerException.class, () -> {
//            containerController.create(request);
//        });
//        assertEquals("Container Addr [address] is duplicated.", exception.getMessage());
//        verify(containerService, times(1)).create(request);
//    }
//
//    @Test
//    void testCreateContainer_DuplicateName() {
//        PostCreateContainerReq request = new PostCreateContainerReq();
//        when(containerService.create(any(PostCreateContainerReq.class))).thenThrow(new ContainerException(ErrorCode.ContainerCreate_004, "Container Name [name] is duplicated."));
//        ContainerException exception = assertThrows(ContainerException.class, () -> {
//            containerController.create(request);
//        });
//        assertEquals("Container Name [name] is duplicated.", exception.getMessage());
//        verify(containerService, times(1)).create(request);
//    }
//
//    @Test
//    void testCreateContainer_InvalidContainer() {
//        PostCreateContainerReq request = new PostCreateContainerReq();
//        when(containerService.create(any(PostCreateContainerReq.class))).thenThrow(new ContainerException(ErrorCode.ContainerCreate_005, "Container Idx [1] is not Exist."));
//        ContainerException exception = assertThrows(ContainerException.class, () -> {
//            containerController.create(request);
//        });
//        assertEquals("Container Idx [1] is not Exist.", exception.getMessage());
//        verify(containerService, times(1)).create(request);
//    }
//
//    @Test
//    void testCreateContainerProduct_InvalidContainerIdx() {
//        ContainerCreateProductReq request = new ContainerCreateProductReq();
//        when(containerService.createContainerProduct(any(ContainerCreateProductReq.class))).thenThrow(new ContainerException(ErrorCode.ContainerCreate_005, "Invalid Container Idx."));
//        ContainerException exception = assertThrows(ContainerException.class, () -> {
//            containerController.createContainerProduct(request);
//        });
//        assertEquals("Invalid Container Idx.", exception.getMessage());
//        verify(containerService, times(1)).createContainerProduct(request);
//    }
//
//    @Test
//    void testList_InvalidPageNumber() {
//        when(containerService.list(anyInt(), anyInt())).thenThrow(new ContainerException(ErrorCode.ContainerList_003, "Invalid page number."));
//        ContainerException exception = assertThrows(ContainerException.class, () -> {
//            containerController.list(0, 10); // Invalid page number (less than 1)
//        });
//        assertEquals("Invalid page number.", exception.getMessage());
//        verify(containerService, times(1)).list(anyInt(), anyInt());
//    }
//
////    @Test
////    void testSingleStockProduct_InvalidContainerIdx() {
////        when(containerService.singleStockProduct(anyInt())).thenThrow(new ContainerException(ErrorCode.ContainerSingleStock_002, "Invalid container index."));
////        ContainerException exception = assertThrows(ContainerException.class, () -> {
////            containerController.singleStockProduct(100); // Invalid container index
////        });
////        assertEquals("Invalid container index.", exception.getMessage());
////        verify(containerService, times(1)).singleStockProduct(anyInt());
////    }
//
//    @Test
//    void testDiscardExpiredFoodProducts_NoProductsToDiscard() {
//        when(containerService.discardExpiredFoodProducts()).thenThrow(new ContainerStockException(ErrorCode.ContainerStock_002, "No products to discard."));
//        ContainerStockException exception = assertThrows(ContainerStockException.class, () -> {
//            containerController.discardExpiredFoodProducts();
//        });
//        assertEquals("No products to discard.", exception.getMessage());
//        verify(containerService, times(1)).discardExpiredFoodProducts();
//    }
//}