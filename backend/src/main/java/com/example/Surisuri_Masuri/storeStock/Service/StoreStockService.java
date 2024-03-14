package com.example.Surisuri_Masuri.storeStock.Service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Repository.UserRepository;
import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.repository.ProductRepository;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import com.example.Surisuri_Masuri.store.Model.ReqDtos.StoreSearchReq;
import com.example.Surisuri_Masuri.store.Model.ResDtos.StoreReadRes;
import com.example.Surisuri_Masuri.store.Repository.StoreRepository;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import com.example.Surisuri_Masuri.storeStock.Model.ReqDtos.StoreStockCreateReq;
import com.example.Surisuri_Masuri.store.Model.ResDtos.StoreDto;
import com.example.Surisuri_Masuri.storeStock.Model.ReqDtos.StoreStockDeleteReq;
import com.example.Surisuri_Masuri.storeStock.Model.ReqDtos.StoreStockSearchReq;
import com.example.Surisuri_Masuri.storeStock.Model.ReqDtos.StoreStockUpdateReq;
import com.example.Surisuri_Masuri.storeStock.Model.ResDtos.*;
import com.example.Surisuri_Masuri.storeStock.Repository.StoreStockRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreStockService {

    private final StoreStockRepository storeStockRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private  StoreStockCreateRes storeStockCreateRes;

    private StoreStockReadRes storeStockReadRes;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    // 가맹점 재고 등록
    public BaseResponse<StoreStockCreateRes> StoreStockCreate(String token, StoreStockCreateReq storeStockCreateReq) {

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Date update = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());
        Date create = Date.from(localDateTime.atZone(ZoneId.of("Asia/Seoul")).toInstant());

        token = JwtUtils.replaceToken(token);

        String email = JwtUtils.getUserEmail(token, secretKey);
        String storeUuid = JwtUtils.getStoreUuid(token, secretKey);

        Optional<User> user = userRepository.findByUserEmail(email);
        Optional<Store> store = storeRepository.findByStoreUuid(storeUuid);

        Store store2 = store.get();

        if (user.isPresent()) {
            // productIdx를 통해서 등록하고자하는 상품을 찾고
            // 재고를 생성할때 상품의 정보를 입력해줌과 동시에 재고의 수량도 함꼐 지정
            Optional<Product> product = productRepository.findById(storeStockCreateReq.getProductIdx());
            Product product2 = product.get();

            StoreStock storeStock = StoreStock
                    .builder()
                    .product(product2)
                    .createdAt(create)
                    .updatedAt(update)
                    .stockQuantitiy(storeStockCreateReq.getStockQuantitiy())
                    .store(store2)
                    .expiredAt(storeStockCreateReq.getExpiredAt())
                    .build();

            StoreDto storeDto = StoreDto
                    .builder()
                    .StoreAddr(store2.getStoreAddr())
                    .StoreName(store2.getStoreName())
                    .build();

            storeStockRepository.save(storeStock);

            storeStockCreateRes = StoreStockCreateRes
                    .builder()
                    .storeDto(storeDto)
                    .stockQuantitiy(storeStockCreateReq.getStockQuantitiy())
                    .productIdx(storeStockCreateReq.getProductIdx())
                    .expiredAt(storeStock.getExpiredAt())
                    .build();
            return BaseResponse.successResponse("수정된 회원정보입니다.", storeStockCreateRes);
        }
        else
            return BaseResponse.failResponse(7000, "잘못된 요청입니다");
    }

    // 가맹점 재고 리스트 조회
    public BaseResponse<List<StoreStockReadRes>> StoreStockList(String token, Integer page, Integer size) {

        token = JwtUtils.replaceToken(token);

        String email = JwtUtils.getUserEmail(token, secretKey);
        String storeUuid = JwtUtils.getStoreUuid(token, secretKey);

        Optional<User> user = userRepository.findByUserEmail(email);
        Optional<Store> store = storeRepository.findByStoreUuid(storeUuid);

        if (user.isPresent()) {

            Store store2 = store.get();

            List<StoreStock> storeStocksList = store2.getStoreStocks();

            // Pageable pageable = PageRequest.of(page-1,size);

            // Page<StoreStock> result = storeStockRepository.findList(pageable);

            List<StoreStockReadRes> storeSearchResList = new ArrayList<>();

            for (StoreStock storeStock : storeStocksList) {

                String productName = storeStock.getProduct().getProductName();

                StoreStockDto storeStockDto = StoreStockDto
                        .builder()
                        .productName(productName)
                        .expiredAt(storeStock.getExpiredAt())
                        .build();

                storeStockReadRes = StoreStockReadRes
                        .builder()
                        .storeStockDto(storeStockDto)
                        .stockQuantitiy(storeStock.getStockQuantitiy())
                        .build();

                storeSearchResList.add(storeStockReadRes);
            }
            // DtoToRes
            return BaseResponse.successResponse("요청 성공", storeSearchResList);
        }
        else return BaseResponse.failResponse(7000, "요청 실패");

    }

    // 가맹점 재고 단일 조회
    public BaseResponse<StoreStockSearchRes> StoreStockSearch(String token, StoreStockSearchReq storeStockSearchReq) {

        token = JwtUtils.replaceToken(token);

        String email = JwtUtils.getUserEmail(token, secretKey);
        String storeUuid = JwtUtils.getStoreUuid(token, secretKey);

        Optional<User> user = userRepository.findByUserEmail(email);
        Optional<Store> store = storeRepository.findByStoreUuid(storeUuid);
        if (user.isPresent()) {

            Optional<StoreStock> storeStock = storeStockRepository.
                    findStoreStockByProduct_ProductNameAndStore_StoreUuid
                            (storeStockSearchReq.getProductName(),store.get().getStoreUuid());

            StoreStock storeStock2 = storeStock.get();

            StoreStockDto storeStockDto = StoreStockDto
                    .builder()
                    .productName(storeStock2.getProduct().getProductName())
                    .build();

            StoreStockSearchRes storeStockSearchRes = StoreStockSearchRes
                    .builder()
                    .productName(storeStockDto)
                    .stockQuantity(storeStock2.getStockQuantitiy())
                    .storeAddr(store.get().getStoreAddr())
                    .build();

            // DtoToRes
            return BaseResponse.successResponse("요청 성공", storeStockSearchRes);
        }
        else return BaseResponse.failResponse(7000, "요청 실패");

    }

    // 가맹점 재고 수정
    public BaseResponse<StoreStockUpdateRes> StoreStockUpdate(String token, StoreStockUpdateReq storeStockUpdateReq) {

        token = JwtUtils.replaceToken(token);

        String email = JwtUtils.getUserEmail(token, secretKey);
        String storeUuid = JwtUtils.getStoreUuid(token, secretKey);

        Optional<User> user = userRepository.findByUserEmail(email);
        Optional<Store> store = storeRepository.findByStoreUuid(storeUuid);
        if (user.isPresent()) {

            Optional<StoreStock> storeStock = storeStockRepository.
                    findStoreStockByProduct_IdxAndStore_StoreUuid(storeStockUpdateReq.getIdx(),store.get().getStoreUuid());

            StoreStock storeStock2 = storeStock.get();

            storeStock2.setStockQuantitiy(storeStockUpdateReq.getStockQuantity());

            storeStockRepository.save(storeStock2);

            StoreStockUpdateRes storeStockUpdateRes = StoreStockUpdateRes
                    .builder()
                    .productName(storeStock2.getProduct().getProductName())
                    .stockQuantity(storeStockUpdateReq.getStockQuantity())
                    .build();

            // DtoToRes
            return BaseResponse.successResponse("요청 성공", storeStockUpdateRes);
        }
        else return BaseResponse.failResponse(7000, "요청 실패");

    }
    // 가맹점 재고 삭제
    @Transactional
    public BaseResponse<StoreStockDeleteRes> StoreStockDelete(String token, StoreStockDeleteReq storeStockDeleteReq) {

        token = JwtUtils.replaceToken(token);

        String email = JwtUtils.getUserEmail(token, secretKey);
        String storeUuid = JwtUtils.getStoreUuid(token, secretKey);

        Optional<User> user = userRepository.findByUserEmail(email);
        Optional<Store> store = storeRepository.findByStoreUuid(storeUuid);
        if (user.isPresent()) {

            storeStockRepository.
                    deleteStoreStockByProduct_IdxAndStore_StoreUuid(storeStockDeleteReq.getIdx(),store.get().getStoreUuid());

            StoreStockDeleteRes storeStockDeleteRes = StoreStockDeleteRes
                    .builder()
                    .idx(storeStockDeleteReq.getIdx())
                    .build();

            // DtoToRes
            return BaseResponse.successResponse("요청 성공", storeStockDeleteRes);
        }
        else return BaseResponse.failResponse(7000, "요청 실패");

    }
}
