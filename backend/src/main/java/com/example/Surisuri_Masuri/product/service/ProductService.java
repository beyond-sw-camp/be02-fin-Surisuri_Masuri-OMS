package com.example.Surisuri_Masuri.product.service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.model.dto.request.ProductCreateReq;
import com.example.Surisuri_Masuri.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
