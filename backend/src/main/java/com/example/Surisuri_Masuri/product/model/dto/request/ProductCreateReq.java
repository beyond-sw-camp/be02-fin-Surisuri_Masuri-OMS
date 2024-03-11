package com.example.Surisuri_Masuri.product.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCreateReq {
    String productName;
    Integer price;
}
