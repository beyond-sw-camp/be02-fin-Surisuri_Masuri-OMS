package com.example.Surisuri_Masuri.orders.service;

import com.example.Surisuri_Masuri.cart.model.Cart;
import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.cart.repository.CartDetailRepository;
import com.example.Surisuri_Masuri.cart.repository.CartRepository;
import com.example.Surisuri_Masuri.cart.service.CartService;
import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.container.model.entity.Container;
import com.example.Surisuri_Masuri.container.model.entity.ContainerStock;
import com.example.Surisuri_Masuri.container.repository.ContainerRepository;
import com.example.Surisuri_Masuri.container.repository.ContainerStockRepository;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Repository.ManagerRepository;
import com.example.Surisuri_Masuri.member.Repository.UserRepository;
import com.example.Surisuri_Masuri.orders.model.Orders;
import com.example.Surisuri_Masuri.orders.model.OrdersDetail;
import com.example.Surisuri_Masuri.orders.model.dto.PaymentDto;
import com.example.Surisuri_Masuri.orders.model.dto.request.OrdersRefundReq;
import com.example.Surisuri_Masuri.orders.model.dto.request.OrdersUpdateDeliveryReq;
import com.example.Surisuri_Masuri.orders.model.dto.response.OrdersDetailDtoRes;
import com.example.Surisuri_Masuri.orders.model.dto.response.OrdersListRes;
import com.example.Surisuri_Masuri.orders.model.dto.response.OrdersShowDeliveryStatusRes;
import com.example.Surisuri_Masuri.orders.model.dto.response.ProductDtoRes;
import com.example.Surisuri_Masuri.orders.repository.OrdersDetailRepository;
import com.example.Surisuri_Masuri.orders.repository.OrdersRepository;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import com.example.Surisuri_Masuri.store.Repository.StoreRepository;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import com.example.Surisuri_Masuri.storeStock.Repository.StoreStockRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersDetailRepository ordersDetailRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final ContainerRepository containerRepository;
    private final ContainerStockRepository containerStockRepository;
    private final StoreRepository storeRepository;
    private final StoreStockRepository storeStockRepository;

    private final IamportClient iamportClient;

    @Value("${imp.imp_secret}")
    private String apiSecret;

    @Value("${imp.imp_key}")
    private String apiKey;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public BaseResponse listDetailByMerchantUid(String merchantUid, User user) {
        Optional<User> userResult = userRepository.findByUserEmail(user.getUserEmail());
        user = userResult.get();

        for (Orders orders: user.getStore().getOrdersList()) {
            List<OrdersDetail> ordersDetailResult = ordersDetailRepository.findByOrdersIdx(orders.getIdx());

            for (OrdersDetail ordersDetail : ordersDetailResult) {
                if (orders.getMerchantUid().equals(merchantUid)) {
                    OrdersListRes ordersListRes = OrdersListRes.builder()
                            .productDtoRes(ProductDtoRes.builder()
                                    .productName(ordersDetail.getProduct().getProductName())
                                    .price(ordersDetail.getProduct().getPrice())
                                    .productQuantity(ordersDetail.getProcuctQuantity())
                                    .build())
                            .payMethod(orders.getPayMethod())
                            .totalPrice(orders.getTotalPrice())
                            .createdDate(orders.getCreatedAt())
                            .deliveryStatus(orders.getDeliveryStatus())
                            .merchantUid(orders.getMerchantUid())
                            .build();

                    return BaseResponse.successResponse("주문 내역 조회를 성공했습니다.", ordersListRes);
                }
            }
        }
        return BaseResponse.failResponse(444, "merchantUid가 일치하는 주문 내역이 없습니다");
    }

    public BaseResponse updateOrdersDelivery(OrdersUpdateDeliveryReq req) {
        Optional<Orders> ordersResult = ordersRepository.findById(req.getOrdersIdx());
        Orders orders = ordersResult.get();

        Integer containerIdx = null;

        if (req.getDeliveryStatus().equals("출고 처리")) {
            // 창고 재고 변경
            for (OrdersDetail ordersDetail : ordersResult.get().getOrdersDetailList()) {
                containerIdx = selectContainer(ordersDetail.getProduct().getProductName(), ordersDetail.getOrders().getStore().getStoreAddr());
                Optional<Container> containerResult = containerRepository.findById(containerIdx);
                Container container = containerResult.get();
                for (ContainerStock containerStock : container.getContainerStockList()) {
                    if (containerStock.getProduct() == ordersDetail.getProduct()) {
                        Optional<ContainerStock> containerStockResult = containerStockRepository.findById(containerStock.getIdx());
                        Long quantity = containerStockResult.get().getProductQuantity() - ordersDetail.getProcuctQuantity();
                        containerStockResult.get().setProductQuantity(quantity);
                        containerStockRepository.save(containerStockResult.get());

                        orders.setDeliveryStatus(req.getDeliveryStatus().getStatus());

                        ordersRepository.save(orders);

                        return BaseResponse.successResponse("배송 상태 변경 성공", null);
                    }
                }
            }

        } else if (req.getDeliveryStatus().equals("배송 완료")) {
            // 가맹점 재고 변경
            for (OrdersDetail ordersDetail : ordersResult.get().getOrdersDetailList()) {
                // 플래그
                boolean flag = false;

                Optional<Store> storeResult = storeRepository.findById(orders.getStore().getIdx());
                containerIdx = selectContainer(ordersDetail.getProduct().getProductName(), storeResult.get().getStoreAddr());
                Store store = storeResult.get();

                // 창고 상품의 유효기간 가져오기 위한 쿼리
                List<LocalDate> containerStockResult = containerStockRepository
                        .findByContainerIdxAndProductName(containerIdx, ordersDetail.getProduct().getProductName());

                for (StoreStock storeStock : store.getStoreStocks()) {
                    if (storeStock.getProduct() == ordersDetail.getProduct()
                            && storeStock.getExpiredAt().equals(containerStockResult.get(0))) {
                        Optional<StoreStock> storeStockResult = storeStockRepository.findById(storeStock.getIdx());
                        Long quantity = storeStockResult.get().getStockQuantitiy() + ordersDetail.getProcuctQuantity();
                        storeStockResult.get().setStockQuantitiy(quantity);
                        storeStockRepository.save(storeStockResult.get());

                        orders.setDeliveryStatus(req.getDeliveryStatus().getStatus());

                        ordersRepository.save(orders);

                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    storeStockRepository.save(StoreStock.builder()
                            .store(store)
                            .product(ordersDetail.getProduct())
                            .stockQuantitiy(ordersDetail.getProcuctQuantity().longValue())
                            .expiredAt(containerStockResult.get(0))
                            .isDiscarded(false)
                            .build());

                    orders.setDeliveryStatus(req.getDeliveryStatus().getStatus());

                    ordersRepository.save(orders);
                }
            }
            return BaseResponse.successResponse("배송 상태 변경 성공", null);
        }

            orders.setDeliveryStatus(req.getDeliveryStatus().getStatus());

        ordersRepository.save(orders);

        return BaseResponse.successResponse("배송 상태 변경을 성공했습니다.", null);
    }

    public Integer selectContainer(String productName, String storeAddr) {
        // 경기도 서울 인천 / 강원도 / 충청남북 대전 / 전라남북 / 경상남북 부산 대구 / 제주
        List<Container> containerList = containerRepository.findAll();
        for (Container container : containerList) {
            String containerRegion = getRegionFromAddr(container.getContainerAddr());
            String storeRegion = getRegionFromAddr(storeAddr);

            if (containerRegion.equals(storeRegion)) {
                for (ContainerStock containerStock : container.getContainerStockList()) {
                    if (containerStock.getProduct().getProductName().equals(productName)) {
                        return container.getIdx();
                    }
                }
            }
        }

        return null;
    }

    private String getRegionFromAddr(String address) {

        // 주소의 첫 번째 토큰을 추출하여 지역을 판단
        String[] tokens = address.split("\\s+");

        // 주소의 시작 부분에 따라 지역을 판단하여 반환
        if (tokens[0].equals("서울시")) {
            return "경기도";
        } else if (tokens[0].equals("부산시")) {
            return "경상남도";
        }

        return tokens[0];
    }

    public BaseResponse showDeliveryStatus(Long ordersIdx) {
        Optional<Orders> ordersResult = ordersRepository.findById(ordersIdx);
        Orders orders = ordersResult.get();
        List<OrdersDetail> ordersDetailList = ordersDetailRepository.findByOrdersIdx(ordersIdx);

        List<OrdersDetailDtoRes> ordersDetailDtoResList = new ArrayList<>();

        for (OrdersDetail ordersDetail : ordersDetailList) {
            OrdersDetailDtoRes ordersDetailDtoRes = OrdersDetailDtoRes.builder()
                    .procuctQuantity(ordersDetail.getProcuctQuantity())
                    .productName(ordersDetail.getProduct().getProductName())
                    .build();

            ordersDetailDtoResList.add(ordersDetailDtoRes);
        }

        OrdersShowDeliveryStatusRes ordersShowDeliveryStatusRes = OrdersShowDeliveryStatusRes.builder()
                .ordersDetailDtoResList(ordersDetailDtoResList)
                .deliveryStatus(orders.getDeliveryStatus())
                .createAt(orders.getCreatedAt())
                .updatedAt(orders.getUpdatedAt())
                .build();

        return BaseResponse.successResponse("요청 성공했습니다.", ordersShowDeliveryStatusRes);
    }

    public BaseResponse list(String token, Integer page, Integer size) {
        token = JwtUtils.replaceToken(token);

        String email = JwtUtils.getUserEmail(token, secretKey);
        String managerId = JwtUtils.getManagerInfo(token, secretKey);

        Optional<User> userResult = userRepository.findByUserEmail(email);
        Optional<Manager> managerResult = managerRepository.findByManagerId(managerId);
        User user = userResult.get();

        if (managerResult.isPresent()) {
            Pageable pageable = PageRequest.of(page - 1, size);

            Page<Orders> ordersResult = ordersRepository.findList(pageable);

            List<OrdersListRes> ordersListResList = new ArrayList<>();

            for (Orders orders : ordersResult) {
                Page<OrdersDetail> ordersDetailsResult = ordersDetailRepository.findListByOrdersIdx(orders.getIdx(), pageable);

                for (OrdersDetail ordersDetail : ordersDetailsResult) {
                    OrdersListRes ordersListRes = OrdersListRes.builder()
                            .productDtoRes(ProductDtoRes.builder()
                                    .productName(ordersDetail.getProduct().getProductName())
                                    .price(ordersDetail.getProduct().getPrice())
                                    .productQuantity(ordersDetail.getProcuctQuantity())
                                    .build())
                            .payMethod(orders.getPayMethod())
                            .totalPrice(orders.getTotalPrice())
                            .createdDate(orders.getCreatedAt())
                            .deliveryStatus(orders.getDeliveryStatus())
                            .merchantUid(orders.getMerchantUid())
                            .build();

                    ordersListResList.add(ordersListRes);
                }
            }

            return BaseResponse.successResponse("상품 리스트 불러오기 성공", ordersListResList);

        } else if (userResult.isPresent()) {
            Pageable pageable = PageRequest.of(page - 1, size);

            Page<Orders> ordersResult = ordersRepository.findListByUserIdx(user.getIdx(), pageable);

            List<OrdersListRes> ordersListResList = new ArrayList<>();

            for (Orders orders : ordersResult) {
                Page<OrdersDetail> ordersDetailsResult = ordersDetailRepository.findListByOrdersIdx(orders.getIdx(), pageable);

                for (OrdersDetail ordersDetail : ordersDetailsResult) {
                    OrdersListRes ordersListRes = OrdersListRes.builder()
                            .productDtoRes(ProductDtoRes.builder()
                                    .productName(ordersDetail.getProduct().getProductName())
                                    .price(ordersDetail.getProduct().getPrice())
                                    .productQuantity(ordersDetail.getProcuctQuantity())
                                    .build())
                            .payMethod(orders.getPayMethod())
                            .totalPrice(orders.getTotalPrice())
                            .createdDate(orders.getCreatedAt())
                            .deliveryStatus(orders.getDeliveryStatus())
                            .merchantUid(orders.getMerchantUid())
                            .build();

                    ordersListResList.add(ordersListRes);
                }
            }
            return BaseResponse.successResponse("상품 목록 조회를 성공했습니다.", ordersListResList);
        }
        return BaseResponse.failResponse(444,"상품 목록 조회를 실패했습니다.");
    }

    public void create(String payMethod, Long cartIdx, String merchantUid, Long amount, Store store) {
        Optional<Cart> cartResult = cartRepository.findById(cartIdx);
        List<CartDetail> cartDetailList = cartDetailRepository.findByCartIdx(cartIdx);

        Orders orders = ordersRepository.save(Orders.builder()
                .store(store)
                .payMethod(payMethod)
                .totalPrice(amount)
                .merchantUid(merchantUid)
                .deliveryStatus("배송 전")
                .build());

        for (CartDetail cartDetail: cartDetailList) {
            ordersDetailRepository.save(OrdersDetail.builder()
                    .product(cartDetail.getProduct())
                    .procuctQuantity(cartDetail.getProductQuantity())
                    .orders(orders)
                    .build());
        }
    }

    public void delete(Long idx) {
        Optional<Orders> ordersResult = ordersRepository.findById(idx);
        List<OrdersDetail> ordersDetailList = ordersDetailRepository.findByOrdersIdx(idx);

        for (OrdersDetail ordersDetail : ordersDetailList) {
            ordersDetailRepository.delete(ordersDetail);
        }
        ordersRepository.delete(ordersResult.get());
    }

    public BaseResponse payment(User user, String imp_uid) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = getPaymentInfo(imp_uid);

        Optional<User> userResult = userRepository.findById(user.getIdx());
        User foundUser = userResult.get();

        String customDataString = response.getResponse().getCustomData();

        String payMethod = response.getResponse().getPgProvider();

        Gson gson = new Gson();
        PaymentDto paymentDto = gson.fromJson(customDataString, PaymentDto.class);
        Long cartIdx = paymentDto.getCartDtoList().get(0).getCartIdx();

        Long amount = response.getResponse().getAmount().longValue();
        String merchantUid = response.getResponse().getMerchantUid();

        Optional<Cart> cartResult = cartRepository.findById(cartIdx);
        List<CartDetail> cartDetailList = cartDetailRepository.findByCartIdx(cartIdx);

        Integer productPrice = null;

        for (CartDetail cartDetail: cartDetailList) {
            productPrice =+ (cartDetail.getProduct().getPrice() * cartDetail.getProductQuantity());
        }
        System.out.println("productPrice = " + productPrice);
        System.out.println("amount = " + amount);

        if (productPrice != amount.intValue()) {
            OrdersRefundReq ordersRefundReq = OrdersRefundReq.builder()
                    .merchantUid(merchantUid)
                    .refundReason("결제 금액과 상품 금액 총합이 맞지 않습니다")
                    .build();
            refundRequest(ordersRefundReq);

            for (CartDetail cartDetail: cartDetailList) {
                cartService.delete(foundUser, cartIdx, cartDetail.getProduct().getProductName());
            }

            return BaseResponse.failResponse(444, "금액 불일치");
        }

        create(payMethod ,cartIdx, merchantUid, amount, foundUser.getStore());

        for (CartDetail cartDetail: cartDetailList) {
            cartService.delete(foundUser, cartIdx, cartDetail.getProduct().getProductName());
        }

        return BaseResponse.successResponse("결제 성공", customDataString);
    }

    public IamportResponse getPaymentInfo(String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = iamportClient.paymentByImpUid(impUid);

        return response;
    }

    public BaseResponse refundRequest(OrdersRefundReq req) throws IOException {
        Orders orders = null;
        if (req.getIdx() != null) {
            Optional<Orders> ordersResult = ordersRepository.findById(req.getIdx());

            orders = ordersResult.get();

            if (!orders.getDeliveryStatus().equals("배송 전")) {
                return BaseResponse.failResponse(444, "배송이 이미 시작되어 주문 취소가 불가능합니다");
            }
        }

        String access_token = getToken(apiKey, apiSecret);

        URL url = new URL("https://api.iamport.kr/payments/cancel");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 요청 방식을 POST로 설정
        conn.setRequestMethod("POST");

        // 요청의 Content-Type, Accept, Authorization 헤더 설정
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", access_token);

        // 해당 연결을 출력 스트림(요청)으로 사용
        conn.setDoOutput(true);

        // JSON 객체에 해당 API가 필요로하는 데이터 추가.
        JsonObject json = new JsonObject();
        if (req.getIdx() != null) {
            json.addProperty("merchant_uid", orders.getMerchantUid());
        } else {
            json.addProperty("merchant_uid", req.getMerchantUid());
        }
        json.addProperty("reason", req.getRefundReason());

        // 출력 스트림으로 해당 conn에 요청
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString());
        bw.flush();
        bw.close();

        // 입력 스트림으로 conn 요청에 대한 응답 반환
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        br.close();
        conn.disconnect();

        // Orders 엔티티에서 주문내역 delete
        if (req.getIdx() != null)
            delete(req.getIdx());

        return BaseResponse.successResponse("환불 성공", null);
    }

    public String getToken(String apiKey, String secretKey) throws IOException {
        URL url = new URL("https://api.iamport.kr/users/getToken");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // 요청 방식을 POST로 설정
        conn.setRequestMethod("POST");

        // 요청의 Content-Type과 Accept 헤더 설정
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        // 해당 연결을 출력 스트림(요청)으로 사용
        conn.setDoOutput(true);

        // JSON 객체에 해당 API가 필요로하는 데이터 추가.
        JsonObject json = new JsonObject();
        json.addProperty("imp_key", apiKey);
        json.addProperty("imp_secret", secretKey);

        // 출력 스트림으로 해당 conn에 요청
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(json.toString()); // json 객체를 문자열 형태로 HTTP 요청 본문에 추가
        bw.flush(); // BufferedWriter 비우기
        bw.close(); // BufferedWriter 종료

        // 입력 스트림으로 conn 요청에 대한 응답 반환
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Gson gson = new Gson(); // 응답 데이터를 자바 객체로 변환
        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();
        String accessToken = gson.fromJson(response, Map.class).get("access_token").toString();
        br.close(); // BufferedReader 종료

        conn.disconnect(); // 연결 종료

//        log.info("Iamport 엑세스 토큰 발급 성공 : {}", accessToken);
        return accessToken;
    }
}
