package com.example.Surisuri_Masuri.container.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.container.model.entity.Container;
import com.example.Surisuri_Masuri.container.model.entity.ContainerStock;
import com.example.Surisuri_Masuri.container.model.request.ContainerCreateProductReq;
import com.example.Surisuri_Masuri.container.model.request.ContainerStockDto;
import com.example.Surisuri_Masuri.container.model.request.PostCreateContainerReq;
import com.example.Surisuri_Masuri.container.model.response.GetListContainerRes;
import com.example.Surisuri_Masuri.container.model.response.GetSingleContainerStockRes;
import com.example.Surisuri_Masuri.container.model.response.PostCreateContainerRes;
import com.example.Surisuri_Masuri.container.repository.ContainerRepository;
import com.example.Surisuri_Masuri.container.repository.ContainerStockRepository;
import com.example.Surisuri_Masuri.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContainerService {

    private final ContainerRepository containerRepository;
    private final ContainerStockRepository containerStockRepository;

    public BaseResponse create(PostCreateContainerReq postCreateContainerReq) {

        Container container = Container.builder()
                .containerName(postCreateContainerReq.getContainerName())
                .containerAddr(postCreateContainerReq.getContainerAddr())
                .containerManager(postCreateContainerReq.getContainerManager())
                .containerPhoneNo(postCreateContainerReq.getContainerPhoneNo())
                .containerComplexity(postCreateContainerReq.getContainerComplexity())
                .build();
        containerRepository.save(container);

        PostCreateContainerRes postCreateContainerRes = PostCreateContainerRes.builder()
                .containerName(postCreateContainerReq.getContainerName())
                .containerAddr(postCreateContainerReq.getContainerAddr())
                .containerManager(postCreateContainerReq.getContainerManager())
                .containerPhoneNo(postCreateContainerReq.getContainerPhoneNo())
                .containerComplexity(postCreateContainerReq.getContainerComplexity())
                .build();

        return BaseResponse.successResponse("창고 등록 성공",postCreateContainerRes);
    }

    public BaseResponse createContainerProduct(ContainerCreateProductReq req) {

        containerStockRepository.save(ContainerStock.builder()
                .container(Container.builder().idx(req.getContainerIdx()).build())
                .product(Product.builder().idx(req.getProductIdx()).build())
                .productQuantity(req.getQuantity())
                .expiredAt(req.getExpiredAt())
                .isDiscarded(false)
                .build());

        return BaseResponse.successResponse("창고 상품 등록 성공", null);
    }

    public BaseResponse list(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page - 1, size);

        List<Container> containerList = containerRepository.findAll();

        List<GetListContainerRes> getListContainerResList = new ArrayList<>();
        for (Container container : containerList) {
            GetListContainerRes getListContainernRes = GetListContainerRes.builder()
                    .containerName(container.getContainerName())
                    .containerAddr(container.getContainerAddr())
                    .containerManager(container.getContainerManager())
                    .containerPhoneNo(container.getContainerPhoneNo())
                    .containerComplexity(container.getContainerComplexity())
                    .build();

            getListContainerResList.add(getListContainernRes);
        }

        return BaseResponse.successResponse("창고 목록 조회 성공", getListContainerResList);

    }


    public BaseResponse singleStockProduct(Integer containerIdx) {

        List<ContainerStock> result = containerStockRepository.findByContainerIdx(containerIdx);

        List<GetSingleContainerStockRes> getSingleContainerStockResList = new ArrayList<>();

        for (ContainerStock containerStock : result) {
            GetSingleContainerStockRes getSingleContainerStockRes = GetSingleContainerStockRes.builder()
                    .containerName(containerStock.getContainer().getContainerName())
                    .productName(containerStock.getProduct().getProductName())
                    .productQuantity(containerStock.getProductQuantity())
                    .expiredAt(containerStock.getExpiredAt())
                    .build();

            getSingleContainerStockResList.add(getSingleContainerStockRes);
        }

        return BaseResponse.successResponse("창고 정보 조회 성공", getSingleContainerStockResList);
    }

    public BaseResponse<List<ContainerStockDto>> discardExpiredFoodProducts() {
        // 현재 날짜를 기준으로 1주일 전의 날짜를 계산합니다.
        LocalDate currentDate = LocalDate.now().plusDays(7);
        // 유통기한이 1주일 남은 식품 상품들을 조회합니다.
        List<ContainerStock> expiredFoodProducts = containerStockRepository.findExpiredFoodProducts(currentDate);

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

