package com.example.Surisuri_Masuri.cart.service;

import com.example.Surisuri_Masuri.cart.model.Cart;
import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.cart.model.dto.request.CartCreateReq;
import com.example.Surisuri_Masuri.cart.model.dto.response.CartCreateRes;
import com.example.Surisuri_Masuri.cart.model.dto.response.CartListRes;
import com.example.Surisuri_Masuri.cart.repository.CartDetailRepository;
import com.example.Surisuri_Masuri.cart.repository.CartRepository;
import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.exception.EntityException.CartException;
import com.example.Surisuri_Masuri.exception.EntityException.ManagerException;
import com.example.Surisuri_Masuri.exception.EntityException.UserException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Repository.UserRepository;
import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.repository.ProductRepository;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import com.example.Surisuri_Masuri.store.Repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public BaseResponse addCart(User user, CartCreateReq req) {

        Optional<Product> productResult = productRepository.findById(req.getProductIdx());

        Optional<User> userResult = userRepository.findByUserEmail(user.getUserEmail());
        User foundUser = userResult.get();
        Optional<Store> storeResult = storeRepository.findByStoreUuid(foundUser.getStore().getStoreUuid());

        Store store = storeResult.get();

        Product product = productResult.get();

        if (foundUser.getStore().getCartList().size() == 0) {
            Cart newCart = cartRepository.save(Cart.builder()
                    .store(store)
                    .build());

            CartDetail cartDetail = cartDetailRepository.save(CartDetail.builder()
                    .cart(newCart)
                    .product(product)
                    .productQuantity(req.getProductQuantity())
                    .build());

            CartCreateRes cartCreateRes = CartCreateRes.builder()
                    .idx(newCart.getIdx())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .productQuantity(cartDetail.getProductQuantity())
                    .build();

            return BaseResponse.successResponse("요청 성공했습니다.", cartCreateRes);
        } else {
            Optional<Cart> cartResult = cartRepository.findById(foundUser.getStore().getCartList().get(0).getIdx());
            Cart cart =  cartResult.get();

            List<CartDetail> cartDetailResult = cartDetailRepository.findByCartIdx(cart.getIdx());

            if (!cartDetailResult.isEmpty()) {
                for (CartDetail cartDetail : cartDetailResult) {
                    if (cartDetail.getProduct().equals(product)) {
                        cartDetail.setProductQuantity(req.getProductQuantity() + cartDetail.getProductQuantity());
                        cartDetailRepository.save(cartDetail);

                        CartCreateRes cartCreateRes = CartCreateRes.builder()
                                .idx(cart.getIdx())
                                .productName(product.getProductName())
                                .price(product.getPrice())
                                .productQuantity(cartDetail.getProductQuantity())
                                .build();

                        return BaseResponse.successResponse("요청 성공했습니다.", cartCreateRes);
                    }
                }
            }

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

            return BaseResponse.successResponse("요청 성공했습니다.", cartCreateRes);
        }
    }

    public BaseResponse list(User user, Integer page, Integer size) {
        Optional<User> userResult = userRepository.findByUserEmail(user.getUserEmail());
        if (userResult.isPresent()) {
            user = userResult.get();

            Pageable pageable = PageRequest.of(page - 1, size);

            Optional<Cart> cartResult = cartRepository.findById(user.getStore().getCartList().get(0).getIdx());

            Cart cart = cartResult.get();

            Page<CartDetail> cartDetailList = cartDetailRepository.findList(cart.getIdx(), pageable);

            List<CartListRes> cartListResList = new ArrayList<>();

            for (CartDetail cartDetail : cartDetailList) {
                CartListRes cartListRes = CartListRes.builder()
                        .cartIdx(cart.getIdx())
                        .productName(cartDetail.getProduct().getProductName())
                        .price(cartDetail.getProduct().getPrice())
                        .productQuantity(cartDetail.getProductQuantity())
                        .build();

                cartListResList.add(cartListRes);
            }

            return BaseResponse.successResponse("카트 목록 조회를 성공했습니다.", cartListResList);
        }
        throw new ManagerException(ErrorCode.CartList_004,
                String.format("카트 목록 조회에 실패했습니다."));
    }


    public BaseResponse delete(User user, Long cartIdx, String productName) {
        Optional<User> userResult = userRepository.findByUserEmail(user.getUserEmail());
        if (userResult.isPresent()) {
            user = userResult.get();
            Optional<Cart> cartResult = cartRepository.findById(cartIdx);
            Cart cart = cartResult.get();

            if (user.getStore().getIdx().equals(cart.getStore().getIdx())) {
                List<CartDetail> cartDetailList = cartDetailRepository.findByCartIdx(cartIdx);

                for (CartDetail cartDetail : cartDetailList) {
                    if (cartDetail.getProduct().getProductName().equals(productName)) {
                        cartDetailRepository.delete(cartDetail);

                        return BaseResponse.successResponse("카트에 담긴 상품을 삭제했습니다.", null);
                    }
                }
                throw new ManagerException(ErrorCode.CartDelete_003,
                        String.format("카트 정보가 존재하지 않습니다."));
            } else
                throw new ManagerException(ErrorCode.CartDelete_003,
                        String.format("카트 정보가 존재하지 않습니다."));
        } else
            throw new ManagerException(ErrorCode.INVALID_PERMISION,
                    String.format("권한이 없습니다."));
    }
}
