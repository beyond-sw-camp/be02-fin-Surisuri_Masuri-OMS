package com.example.Surisuri_Masuri.cart.service;

import com.example.Surisuri_Masuri.cart.model.Cart;
import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.cart.model.Store;
import com.example.Surisuri_Masuri.cart.model.dto.request.CartCreateReq;
import com.example.Surisuri_Masuri.cart.model.dto.response.CartCreateRes;
import com.example.Surisuri_Masuri.cart.repository.CartDetailRepository;
import com.example.Surisuri_Masuri.cart.repository.CartRepository;
import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;

    public BaseResponse create(CartCreateReq req) {
        Optional<Product> productResult = productRepository.findById(req.getProductIdx());

        Product product = productResult.get();

        Cart cart = cartRepository.save(Cart.builder()
                .store(Store.builder().idx(req.getStoreIdx()).build())
                .build());

        CartDetail cartDetail = cartDetailRepository.save(CartDetail.builder()
                .cart(cart)
                .product(product)
                .productQuantity(req.getProductQuantity())
                .build());

        CartCreateRes cartCreateRes = CartCreateRes.builder()
                .idx(cart.getIdx())
                .productName(product.getProductName())
                .price(product.getPrice())
                .productQuantity(cartDetail.getProductQuantity())
                .build();

        return BaseResponse.successResponse("요청 성공", cartCreateRes);
    }
}
