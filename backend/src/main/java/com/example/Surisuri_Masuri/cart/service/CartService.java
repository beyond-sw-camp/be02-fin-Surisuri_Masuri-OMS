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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;

    public BaseResponse addCart(CartCreateReq req) {
        Optional<Product> productResult = productRepository.findById(req.getProductIdx());

        Product product = productResult.get();

        Optional<Cart> cartResult = cartRepository.findById(req.getIdx());

        if (!cartResult.isPresent()) {
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
        } else {
            Cart cart = cartResult.get();

            List<CartDetail> cartDetailResult = cartDetailRepository.findByCartIdx(req.getIdx());

            for (CartDetail cartDetail : cartDetailResult) {
                if (cartDetail.getProduct().getIdx() == req.getProductIdx()) {
                    cartDetail.setProductQuantity(req.getProductQuantity() + cartDetail.getProductQuantity());
                    cartDetailRepository.save(cartDetail);

                    CartCreateRes cartCreateRes = CartCreateRes.builder()
                            .idx(req.getIdx())
                            .productName(product.getProductName())
                            .price(product.getPrice())
                            .productQuantity(cartDetail.getProductQuantity())
                            .build();

                    return BaseResponse.successResponse("요청 성공", cartCreateRes);
                }
            }

            CartDetail cartDetail = cartDetailRepository.save(CartDetail.builder()
                    .cart(Cart.builder().idx(req.getIdx()).build())
                    .product(product)
                    .productQuantity(req.getProductQuantity())
                    .build());

            CartCreateRes cartCreateRes = CartCreateRes.builder()
                    .idx(req.getIdx())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .productQuantity(cartDetail.getProductQuantity())
                    .build();

            return BaseResponse.successResponse("요청 성공", cartCreateRes);
        }
    }

    public BaseResponse delete(Long idx, Long productIdx) {
        Optional<Cart> cartResult = cartRepository.findById(idx);
        List<CartDetail> cartDetailList = cartDetailRepository.findByCartIdx(idx);

        for (CartDetail cartDetail : cartDetailList) {
            if (cartDetail.getProduct().getIdx() == productIdx)
                cartDetailRepository.delete(cartDetail);
        }

        return BaseResponse.successResponse("카트 상품 삭제 성공", null);
    }
}
