package com.example.Surisuri_Masuri.orders.model.dto.response;

import com.example.Surisuri_Masuri.product.model.Product;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrdersDetailDtoRes {
    Integer procuctQuantity;
    String productName;
}
