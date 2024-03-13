package com.example.Surisuri_Masuri.orders.controller;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.orders.model.dto.request.OrdersPaymentReq;
import com.example.Surisuri_Masuri.orders.model.dto.request.OrdersRefundReq;
import com.example.Surisuri_Masuri.orders.model.dto.request.OrdersUpdateDeliveryReq;
import com.example.Surisuri_Masuri.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrdersController {
    private final OrdersService ordersService;

    @RequestMapping(method = RequestMethod.POST, value = "/payment")
    public ResponseEntity payment(OrdersPaymentReq req) {
        try {
            return ResponseEntity.ok().body(ordersService.payment(req));
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
}
