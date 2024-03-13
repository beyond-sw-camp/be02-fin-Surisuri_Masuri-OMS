package com.example.Surisuri_Masuri.orders.service;

import com.example.Surisuri_Masuri.cart.model.Cart;
import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.cart.repository.CartDetailRepository;
import com.example.Surisuri_Masuri.cart.repository.CartRepository;
import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.orders.model.Orders;
import com.example.Surisuri_Masuri.orders.model.OrdersDetail;
import com.example.Surisuri_Masuri.orders.model.dto.request.OrdersPaymentReq;
import com.example.Surisuri_Masuri.orders.model.dto.request.OrdersRefundReq;
import com.example.Surisuri_Masuri.orders.model.dto.response.OrdersListRes;
import com.example.Surisuri_Masuri.orders.model.dto.response.ProductDtoRes;
import com.example.Surisuri_Masuri.orders.repository.OrdersDetailRepository;
import com.example.Surisuri_Masuri.orders.repository.OrdersRepository;
import com.example.Surisuri_Masuri.product.model.Product;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.text.html.Option;
import java.io.*;
import java.net.URL;
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

    private final IamportClient iamportClient;

    @Value("${imp.imp_secret}")
    private String apiSecret;

    @Value("${imp.imp_key}")
    private String apiKey;



    public BaseResponse list(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<Orders> ordersResult = ordersRepository.findAll();
        List<OrdersDetail> ordersDetailsResult = ordersDetailRepository.findAll();

        List<OrdersListRes> ordersListResList = new ArrayList<>();

        for (Orders orders: ordersResult) {
            List<OrdersDetail> ordersDetailResult = ordersDetailRepository.findByOrdersIdx(orders.getIdx());

            for (OrdersDetail ordersDetail: ordersDetailsResult) {
                OrdersListRes ordersListRes = OrdersListRes.builder()
                        .productDtoRes(ProductDtoRes.builder()
                                .productName(ordersDetail.getProduct().getProductName())
                                .price(ordersDetail.getProduct().getPrice())
                                .productQuantity(ordersDetail.getProcuctQuantity())
                                .build())
                        .payMethod(orders.getPayMethod())
                        .totalPrice(orders.getTotalPrice())
                        .date(orders.getCreatedAt())
                        .build();

                ordersListResList.add(ordersListRes);
            }
        }

        return BaseResponse.successResponse("상품 리스트 검색 성공", ordersListResList);
    }

    public void create(OrdersPaymentReq req, String merchantUid, Long amount) {
        Optional<Cart> cartResult = cartRepository.findById(req.getCartIdx());
        List<CartDetail> cartDetailList = cartDetailRepository.findByCartIdx(req.getCartIdx());

        Orders orders = ordersRepository.save(Orders.builder()
                .payMethod(req.getPayMethod())
                .totalPrice(amount)
                .merchantUid(merchantUid)
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

    public BaseResponse payment(OrdersPaymentReq req) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = getPaymentInfo(req.getImpUid());
        
        Long amount = response.getResponse().getAmount().longValue();
        String merchantUid = response.getResponse().getMerchantUid();

        Optional<Cart> cartResult = cartRepository.findById(req.getCartIdx());
        List<CartDetail> cartDetailList = cartDetailRepository.findByCartIdx(req.getCartIdx());
        
        Integer productPrice = null;

        for (CartDetail cartDetail: cartDetailList) {
            productPrice =+ cartDetail.getProduct().getPrice();
        }
        System.out.println("productPrice = " + productPrice);
        System.out.println("amount = " + amount);

        if (productPrice != amount.intValue()) {
            OrdersRefundReq ordersRefundReq = OrdersRefundReq.builder()
                    .idx(null)
                    .merchantUid(merchantUid)
                    .refundReason("결제 금액과 상품 금액 총합이 맞지 않습니다")
                    .build();
            refundRequest(ordersRefundReq);

            return BaseResponse.failResponse(444, "금액 불일치");
        }

        create(req, merchantUid, amount);
        
        return BaseResponse.successResponse("결제 성공", response.getResponse());
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
