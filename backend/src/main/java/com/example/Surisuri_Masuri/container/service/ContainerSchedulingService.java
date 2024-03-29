package com.example.Surisuri_Masuri.container.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.container.model.entity.ContainerStock;
import com.example.Surisuri_Masuri.container.model.request.ContainerStockDto;
import com.example.Surisuri_Masuri.container.repository.ContainerRepository;
import com.example.Surisuri_Masuri.container.repository.ContainerStockRepository;
import com.example.Surisuri_Masuri.exception.EntityException.ContainerStockException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContainerSchedulingService {
    private final ContainerStockRepository containerStockRepository;

    public BaseResponse<List<ContainerStockDto>> discardExpiredFoodProducts() {

        // 현재 날짜를 기준으로 1주일 전의 날짜를 계산합니다.
        LocalDate currentDate = LocalDate.now().plusDays(7);

        // 유통기한이 1주일 남은 식품 상품들을 조회합니다.
        List<ContainerStock> expiredFoodProducts = containerStockRepository.findExpiredFoodProducts(currentDate);

        if(expiredFoodProducts.isEmpty())
        {
            throw new ContainerStockException(ErrorCode.ContainerStock_002,("페기할 상품이 존재하지 않습니다."));
        }

        // 조회된 상품들을 폐기 처리합니다.
        for (ContainerStock containerStock : expiredFoodProducts) {
            containerStock.setIsDiscarded(true);
            containerStock.setDiscardedAt(new Timestamp(System.currentTimeMillis()));
            containerStockRepository.save(containerStock);
        }

        // 폐기 처리된 상품 정보를 DTO로 변환하여 반환합니다.
        List<ContainerStockDto> discardedProductsDTO = new ArrayList<>();
        for (ContainerStock containerStock : expiredFoodProducts) {
            ContainerStockDto dto = ContainerStockDto.builder()
                    .containerName(containerStock.getContainer().getContainerName())
                    .productName(containerStock.getProduct().getProductName())
                    .productQuantity(containerStock.getProductQuantity())
                    .expiredAt(containerStock.getExpiredAt()) // 변경된 부분
                    .build();
            discardedProductsDTO.add(dto);
        }

        return BaseResponse.successResponse("유통기한이 1주일 남은 식품 상품들을 폐기 처리하였습니다.", discardedProductsDTO);
    }

}
