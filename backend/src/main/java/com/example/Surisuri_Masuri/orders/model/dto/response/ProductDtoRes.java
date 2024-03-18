package com.example.Surisuri_Masuri.orders.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDtoRes {
    String productName;
    Integer price;
    Integer productQuantity;
}
