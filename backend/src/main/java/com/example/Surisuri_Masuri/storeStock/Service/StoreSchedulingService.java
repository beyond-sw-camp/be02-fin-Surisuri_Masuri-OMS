package com.example.Surisuri_Masuri.storeStock.Service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.exception.EntityException.ContainerStockException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import com.example.Surisuri_Masuri.storeStock.Model.ResDtos.StoreStockDto;
import com.example.Surisuri_Masuri.storeStock.Repository.StoreStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreSchedulingService {
    private final StoreStockRepository storeStockRepository;

    public BaseResponse discardExpiredFoodProducts() {

        // 현재 날짜를 기준으로 1주일 전의 날짜를 계산합니다.
        LocalDate currentDate = LocalDate.now().plusDays(7);

        // 유통기한이 1주일 남은 식품 상품들을 조회합니다.
        List<StoreStock> expiredFoodProducts = storeStockRepository.findExpiredFoodProducts(currentDate);

        if(expiredFoodProducts.isEmpty())
        {
            throw new ContainerStockException(ErrorCode.ContainerStock_002,("폐기할 상품이 존재하지 않습니다."));
        }

        // 조회된 상품들을 폐기 처리합니다.
        for (StoreStock storeStock : expiredFoodProducts) {
            storeStock.setIsDiscarded(true);
            storeStock.setDiscardedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate());
            storeStockRepository.save(storeStock);
        }

        // 폐기 처리된 상품 정보를 DTO로 변환하여 반환합니다.
        List<StoreStockDto> discardedProductsDTO = new ArrayList<>();
        for (StoreStock storeStock : expiredFoodProducts) {
            StoreStockDto dto = StoreStockDto.builder()
                    .storeName(storeStock.getStore().getStoreName())
                    .productName(storeStock.getProduct().getProductName())
                    .productQuantity(storeStock.getStockQuantity())
                    .expiredAt(storeStock.getExpiredAt()) // 변경된 부분
                    .build();
            discardedProductsDTO.add(dto);
        }

        return BaseResponse.successResponse("유통 기한이 1주일 남은 식품들을 폐기 처리하였습니다.", discardedProductsDTO);
    }

}
