package com.example.Surisuri_Masuri.orders.model.dto.response;

import com.example.Surisuri_Masuri.orders.model.OrdersDetail;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
public class OrdersShowDeliveryStatusRes {
    List<OrdersDetailDtoRes> ordersDetailDtoResList = new ArrayList<>();
    String deliveryStatus;
    Date createAt;
    Date updatedAt;
}
