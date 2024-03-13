package com.example.Surisuri_Masuri.orders.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrdersUpdateDeliveryReq {
    Long idx;
    String deliveryStatus;
}
