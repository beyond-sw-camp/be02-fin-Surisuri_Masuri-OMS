package com.example.Surisuri_Masuri.orders.controller;

import com.example.Surisuri_Masuri.common.BaseResponse;
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

        return ResponseEntity.ok().body(BaseResponse.failResponse(500,"결제 실패"));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/refund")
    public ResponseEntity refund(OrdersRefundReq req) {
        try {
            return ResponseEntity.ok().body(ordersService.refundRequest(req));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(BaseResponse.failResponse(500,"결제 실패"));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(Integer page, Integer size) {
        return ResponseEntity.ok().body(ordersService.list(page, size));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/updatedelivery")
    public ResponseEntity updateOrdersDelivery(OrdersUpdateDeliveryReq req) {

        return ResponseEntity.ok().body(ordersService.updateOrdersDelivery(req));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/deliverystatus")
    public ResponseEntity showDeliveryStatus(Long ordersIdx) {

        return ResponseEntity.ok().body(ordersService.showDeliveryStatus(ordersIdx));
    }
}
