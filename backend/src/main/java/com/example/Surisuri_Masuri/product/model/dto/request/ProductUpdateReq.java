package com.example.Surisuri_Masuri.product.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductUpdateReq {
    Long idx;
    String productName;
    Integer price;
}
