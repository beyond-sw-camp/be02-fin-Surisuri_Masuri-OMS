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
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Repository.UserRepository;
import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.repository.ProductRepository;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import com.example.Surisuri_Masuri.store.Repository.StoreRepository;
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
public class CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    @Value("${jwt.secret-key}")
    private String secretKey;
    public BaseResponse addCart(String token, CartCreateReq req) {

        token = JwtUtils.replaceToken(token);

        String userId = JwtUtils.getUserEmail(token, secretKey);

        Optional<User> user = userRepository.findByUserEmail(userId);

        Optional<Product> productResult = productRepository.findById(req.getProductIdx());

        if (user.isPresent() && productResult.isPresent()) {

            User foundUser = user.get();

            Product product = productResult.get();

            Optional<Store> storeResult = storeRepository.findByStoreUuid(foundUser.getStore().getStoreUuid());

            if (storeResult.isPresent() && foundUser.getStore().getCartList().isEmpty()) {

                Store store = storeResult.get();

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
            } else if (storeResult == null) {
                throw new CartException(ErrorCode.CartAdd_002,
                        String.format("가맹점 정보를 찾을 수 없습니다."));
            } else {

                Optional<Cart> cartResult = cartRepository.findById(foundUser.getStore().getCartList().get(0).getIdx());

                if (cartResult.isPresent()) {
                    Cart cart = cartResult.get();
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
        } else if (user == null){
            throw new CartException(ErrorCode.CartAdd_001,
                    String.format("회원 정보를 찾을 수 없습니다."));
        }
        throw new CartException(ErrorCode.CartAdd_003,
                String.format("상품 정보를 찾을 수 없습니다."));
    }
    public BaseResponse list(String token, Integer page, Integer size) {
        token = JwtUtils.replaceToken(token);
        String userId = JwtUtils.getUserEmail(token, secretKey);
        Optional<User> user = userRepository.findByUserEmail(userId);
        Optional<User> userResult = userRepository.findByUserEmail(user.get().getUserEmail());
        if (userResult.isPresent()) {
            Pageable pageable = PageRequest.of(page - 1, size);
            Optional<Cart> cartResult = cartRepository.findById(user.get().getStore().getCartList().get(0).getIdx());
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
    public BaseResponse delete(String token, Long cartIdx, String productName) {
        token = JwtUtils.replaceToken(token);
        String userId = JwtUtils.getUserEmail(token, secretKey);
        Optional<User> user = userRepository.findByUserEmail(userId);
        Optional<User> userResult = userRepository.findByUserEmail(user.get().getUserEmail());
        if (userResult.isPresent()) {
            Optional<Cart> cartResult = cartRepository.findById(cartIdx);
            Cart cart = cartResult.get();
            if (user.get().getStore().getIdx().equals(cart.getStore().getIdx())) {
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