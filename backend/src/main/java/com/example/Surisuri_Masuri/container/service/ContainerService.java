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
import com.example.Surisuri_Masuri.exception.EntityException.ContainerException;
import com.example.Surisuri_Masuri.exception.EntityException.ContainerStockException;
import com.example.Surisuri_Masuri.exception.EntityException.ProductException;
import com.example.Surisuri_Masuri.exception.EntityException.StoreException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Repository.ManagerRepository;
import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContainerService {

    private final ContainerRepository containerRepository;
    private final ContainerStockRepository containerStockRepository;
    private final ProductRepository productRepository;
    private final ManagerRepository managerRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;
    public BaseResponse create(String token, PostCreateContainerReq postCreateContainerReq) {

        Optional<Manager> manager = managerRepository.findByManagerId(JwtUtils.getManagerId(token,secretKey));

        if(manager.isPresent()) {

            Optional<Container> test1 = containerRepository.findContainerByContainerAddr(postCreateContainerReq.getContainerAddr());
            Optional<Container> test2 = containerRepository.findContainerByContainerName(postCreateContainerReq.getContainerName());

            if (test1.isPresent())
                throw new ContainerException(ErrorCode.ContainerCreate_003, String.format("창고 이름 [ %s ] 이/가 중복입니다.", postCreateContainerReq.getContainerAddr()));

            if (test2.isPresent())
                throw new ContainerException(ErrorCode.ContainerCreate_004, String.format("창고 이름 [ %s ] 이/가 중복입니다.", postCreateContainerReq.getContainerName()));


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


            return BaseResponse.successResponse("창고 등록 성공", postCreateContainerRes);
        }
        else {
            throw new StoreException(ErrorCode.ContainerCreate_006,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }
    public BaseResponse createContainerProduct(String token, ContainerCreateProductReq req) {

        Optional<Manager> manager = managerRepository.findByManagerId(JwtUtils.getManagerId(token,secretKey));

        if(manager.isPresent()) {

            Optional<Container> test1 = containerRepository.findById(req.getContainerIdx());
            Optional<Product> test2 = productRepository.findById(req.getProductIdx());

            if (test1.isEmpty())
                throw new ContainerException(ErrorCode.ContainerCreate_005, String.format("창고 Idx [ %s ] 이/가 존재하지 않습니다.", req.getContainerIdx()));

            if (test2.isEmpty())
                throw new ProductException(ErrorCode.ProductSearch_002, String.format("상품 Idx [ %s ] 이/가 존재하지 않습니다.", req.getProductIdx()));


            containerStockRepository.save(ContainerStock.builder()
                    .container(Container.builder().idx(req.getContainerIdx()).build())
                    .product(Product.builder().idx(req.getProductIdx()).build())
                    .productQuantity(req.getQuantity())
                    .expiredAt(req.getExpiredAt())
                    .isDiscarded(false)
                    .build());

            return BaseResponse.successResponse("창고 상품 등록을 성공했습니다.", null);
        }
        else {
            throw new StoreException(ErrorCode.ContainerCreate_006,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }
    public BaseResponse list(String token, Integer page, Integer size) {

        Optional<Manager> manager = managerRepository.findByManagerId(JwtUtils.getManagerId(token,secretKey));

        if(manager.isPresent()) {
            Pageable pageable = PageRequest.of(page - 1, size);

            Page<Container> containerList = containerRepository.findList(pageable);

            List<GetListContainerRes> getListContainerResList = new ArrayList<>();
            for (Container container : containerList) {
                GetListContainerRes getListContainernRes = GetListContainerRes.builder()
                        .containerIdx(container.getIdx())
                        .containerName(container.getContainerName())
                        .containerAddr(container.getContainerAddr())
                        .containerManager(container.getContainerManager())
                        .containerPhoneNo(container.getContainerPhoneNo())
                        .containerComplexity(container.getContainerComplexity())
                        .build();

                getListContainerResList.add(getListContainernRes);
            }

            return BaseResponse.successResponse("창고 목록 조회를 성공했습니다.", getListContainerResList);
        }
        else {
            throw new StoreException(ErrorCode.ContainerCreate_006,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }
    public BaseResponse singleStockProduct(String token, Integer containerIdx, Integer page, Integer size) {

        Optional<Manager> manager = managerRepository.findByManagerId(JwtUtils.getManagerId(token, secretKey));

        if (manager.isPresent()) {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<ContainerStock> containerStockList = containerStockRepository.findList(containerIdx, pageable);

            if (containerStockList.isEmpty())
                throw new ContainerException(ErrorCode.ContainerCreate_005,
                        String.format("창고 Idx [ %s ] 에 상품이 존재하지 않습니다.", containerIdx));

            List<GetSingleContainerStockRes> getSingleContainerStockResList = new ArrayList<>();

            for (ContainerStock containerStock : containerStockList) {
                if (!containerStock.getIsDiscarded()) {
                    GetSingleContainerStockRes getSingleContainerStockRes = GetSingleContainerStockRes.builder()
                            .containerName(containerStock.getContainer().getContainerName())
                            .productName(containerStock.getProduct().getProductName())
                            .productQuantity(containerStock.getProductQuantity())
                            .expiredAt(containerStock.getExpiredAt())
                            .build();

                    getSingleContainerStockResList.add(getSingleContainerStockRes);
                }
            }

            return BaseResponse.successResponse("창고 정보 조회를 성공했습니다.", getSingleContainerStockResList);
        } else {
            throw new StoreException(ErrorCode.ContainerCreate_006,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }
}

