package com.example.Surisuri_Masuri.container.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.container.model.entity.Container;
import com.example.Surisuri_Masuri.container.model.entity.ContainerStock;
import com.example.Surisuri_Masuri.container.model.request.PostCreateContainerReq;
import com.example.Surisuri_Masuri.container.model.response.GetListContainerRes;
import com.example.Surisuri_Masuri.container.model.response.GetSingleContainerStockRes;
import com.example.Surisuri_Masuri.container.model.response.PostCreateContainerRes;
import com.example.Surisuri_Masuri.container.repository.ContainerRepository;
import com.example.Surisuri_Masuri.container.repository.ContainerStockRepository;
import com.example.Surisuri_Masuri.notice.model.entity.Notice;
import com.example.Surisuri_Masuri.notice.model.response.PostCreateNoticeRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


    public BaseResponse singlestock(Integer idx) {

        List<ContainerStock> result = containerStockRepository.findByContainerIdx(idx);

        List<GetSingleContainerStockRes> getSingleContainerStockResList = new ArrayList<>();

        for (ContainerStock containerStock : result) {
            GetSingleContainerStockRes getSingleContainerStockRes = GetSingleContainerStockRes.builder()
                    .containerName(containerStock.getContainer().getContainerName())
                    .productName(containerStock.getProduct().getProductName())
                    .productQuantity(containerStock.getProductQuantity())
                    .createdAt(containerStock.getCreatedAt())
                    .build();

            getSingleContainerStockResList.add(getSingleContainerStockRes);
        }

        return BaseResponse.successResponse("창고 정보 조회 성공", getSingleContainerStockResList);
    }


}
