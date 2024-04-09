package com.example.Surisuri_Masuri.orders.model.dto.request;

import com.example.Surisuri_Masuri.orders.model.dto.deliveryEnum.DeliveryEnum;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrdersUpdateDeliveryReq {
    Long ordersIdx;

    private String deliveryStatus;

    public DeliveryEnum getDeliveryStatus() {
        return DeliveryEnum.fromString(deliveryStatus);
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}