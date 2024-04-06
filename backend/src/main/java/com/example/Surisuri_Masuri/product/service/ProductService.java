package com.example.Surisuri_Masuri.product.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.exception.EntityException.ProductException;
import com.example.Surisuri_Masuri.exception.EntityException.StoreException;
import com.example.Surisuri_Masuri.exception.EntityException.UserException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Repository.ManagerRepository;
import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.model.dto.request.ProductCreateReq;
import com.example.Surisuri_Masuri.product.model.dto.request.ProductUpdateReq;
import com.example.Surisuri_Masuri.product.model.dto.response.ProductReadRes;
import com.example.Surisuri_Masuri.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ManagerRepository managerRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public BaseResponse create(String token,ProductCreateReq req) {

        token = JwtUtils.replaceToken(token);

        String managerId = JwtUtils.getManagerInfo(token, secretKey);

        Optional<Manager> manager = managerRepository.findByManagerId(managerId);

        if (manager.isPresent()) {
            Product product = productRepository.save(Product.builder()
                    .productName(req.getProductName())
                    .isItFood(req.getIsItFood())
                    .expiredAt(req.getExpiredAt())
                    .price(req.getPrice())
                    .productCategory(req.getProductCategory())
                    .build());

            return BaseResponse.successResponse("요청 성공했습니다.", null);
        } else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }

    public BaseResponse search(String token,String productName, Integer page, Integer size) {

        token = JwtUtils.replaceToken(token);

        String managerId = JwtUtils.getManagerInfo(token, secretKey);

        Optional<Manager> manager = managerRepository.findByManagerId(managerId);

        if (manager.isPresent()) {

            Pageable pageable = PageRequest.of(page - 1, size);

            Page<Product> result = productRepository.findByProductNameContaining(productName, pageable);

            List<ProductReadRes> productReadResList = new ArrayList<>();

            for (Product product : result.getContent()) {

                ProductReadRes productReadRes = ProductReadRes
                        .builder()
                        .productIdx(product.getIdx())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .productCategory(product.getProductCategory())
                        .build();

                productReadResList.add(productReadRes);
            }

            return BaseResponse.successResponse("상품 검색을 성공했습니다.", productReadResList);
        }
        else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        } // 여기까지
    }

    public BaseResponse list(String token,Integer page, Integer size) {

        token = JwtUtils.replaceToken(token);

        String managerId = JwtUtils.getManagerInfo(token, secretKey);

        Optional<Manager> manager = managerRepository.findByManagerId(managerId);

        if (manager.isPresent()) {

            Pageable pageable = PageRequest.of(page - 1, size);

            Page<Product> result = productRepository.findList(pageable);

            List<ProductReadRes> productReadResList = new ArrayList<>();

            for (Product product : result.getContent()) {

                ProductReadRes productReadRes = ProductReadRes
                        .builder()
                        .productIdx(product.getIdx())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .productCategory(product.getProductCategory())
                        .build();

                productReadResList.add(productReadRes);
            }
            return BaseResponse.successResponse("상품 목록 조회를 성공했습니다.", productReadResList);
        } else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }

    public BaseResponse update(String token,ProductUpdateReq req) {
        token = JwtUtils.replaceToken(token);

        String managerId = JwtUtils.getManagerInfo(token, secretKey);

        Optional<Manager> manager = managerRepository.findByManagerId(managerId);

        if (manager.isPresent()) {
            Optional<Product> result = productRepository.findById(req.getIdx());

            if (result.isPresent()) {
                Product product = result.get();
                if (req.getProductName() != null) {
                    product.setProductName(req.getProductName());
                }
                if (req.getPrice() != null) {
                    product.setPrice(req.getPrice());
                }
                productRepository.save(product);

                return BaseResponse.successResponse("상품 수정을 성공했습니다.", null);
            } else throw new ProductException(ErrorCode.ProductUpdate_002, String.format("상품이 존재하지 않습니다."));
        } else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }

    public BaseResponse delete(String token,Long idx) {
        token = JwtUtils.replaceToken(token);

        String managerId = JwtUtils.getManagerInfo(token, secretKey);

        Optional<Manager> manager = managerRepository.findByManagerId(managerId);

        if (manager.isPresent()) {
            Optional<Product> result = productRepository.findById(idx);

            if (result.isPresent()) {
                Product product = result.get();

                productRepository.delete(product);

                return BaseResponse.successResponse("상품 삭제를 성공했습니다.", null);
            } else throw new ProductException(ErrorCode.ProductDelete_002, String.format("상품이 존재하지 않습니다."));
        }
        else {
            throw new UserException(ErrorCode.UNREGISTERD_USER_VALUE,
                    String.format("가입되지 않은 본사 관리자입니다."));
        }
    }
}
