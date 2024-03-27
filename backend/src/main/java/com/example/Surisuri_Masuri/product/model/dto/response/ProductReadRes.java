package com.example.Surisuri_Masuri.product.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductReadRes {
    private Long productIdx;
    private String productName;
    private Integer price;
}
