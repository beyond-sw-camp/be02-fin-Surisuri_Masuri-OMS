package com.example.Surisuri_Masuri.product.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.model.dto.request.ProductCreateReq;
import com.example.Surisuri_Masuri.product.model.dto.request.ProductUpdateReq;
import com.example.Surisuri_Masuri.product.model.dto.response.ProductSearchRes;
import com.example.Surisuri_Masuri.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public BaseResponse create(ProductCreateReq req) {
        productRepository.save(Product.builder()
                .productName(req.getProductName())
                .price(req.getPrice())
                .build());

        return BaseResponse.successResponse("요청 성공", null);
    }

    public BaseResponse search(String productName) {
        Optional<Product> result = productRepository.findByProductName(productName);

        if(result.isPresent()) {
            Product product = result.get();
            ProductSearchRes res = ProductSearchRes.builder()
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .createdAt(product.getCreatedAt())
                    .updatedAt(product.getUpdatedAt())
                    .build();
            return BaseResponse.successResponse("상품 검색 성공", res);
        }

        return BaseResponse.successResponse("검색과 일치하는 상품이 존재하지 않습니다", null);
    }

    public BaseResponse list(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Product> result = productRepository.findAll();

        return BaseResponse.successResponse("상품 리스트 검색 성공", result);

    }

    public BaseResponse update(ProductUpdateReq req) {
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
        }

        return BaseResponse.successResponse("상품 수정 성공", result);
    }
}