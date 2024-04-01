package com.example.Surisuri_Masuri.orders.controller;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.exception.EntityException.UserException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.orders.model.dto.request.OrdersRefundReq;
import com.example.Surisuri_Masuri.orders.model.dto.request.OrdersUpdateDeliveryReq;
import com.example.Surisuri_Masuri.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrdersController {
    private final OrdersService ordersService;

    @RequestMapping(method = RequestMethod.POST, value = "/payment")
    public ResponseEntity payment(@AuthenticationPrincipal User user, @RequestBody String imp_uid) {
        try {
            return ResponseEntity.ok().body(ordersService.payment(user, imp_uid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new UserException(ErrorCode.OrdersPayment_002,
                String.format("결제를 실패했습니다."));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/refund")
    public ResponseEntity refund(OrdersRefundReq req) {
        try {
            return ResponseEntity.ok().body(ordersService.refundRequest(req));
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new UserException(ErrorCode.OrdersPayment_002,
                String.format("결제를 실패했습니다."));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(@RequestHeader(value = "Authorization") String token,
                               Integer page, Integer size) {
        return ResponseEntity.ok().body(ordersService.list(token, page, size));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/updatedelivery")
    public ResponseEntity updateOrdersDelivery(@RequestBody OrdersUpdateDeliveryReq req) {

        return ResponseEntity.ok().body(ordersService.updateOrdersDelivery(req));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deliverystatus")
    public ResponseEntity showDeliveryStatus(Long ordersIdx) {

        return ResponseEntity.ok().body(ordersService.showDeliveryStatus(ordersIdx));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{merchantUid}")
    public ResponseEntity listDetailByMerchantUid(@PathVariable String merchantUid,
                                                  @AuthenticationPrincipal User user) {

        return ResponseEntity.ok().body(ordersService.listDetailByMerchantUid(merchantUid, user));
    }
}
