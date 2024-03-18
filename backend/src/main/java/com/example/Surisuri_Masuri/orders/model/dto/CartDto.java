package com.example.Surisuri_Masuri.orders.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class CartDto {
    private Long cartIdx;
    private String productName;
    private Integer price;
    private Integer productQuantity;
}
