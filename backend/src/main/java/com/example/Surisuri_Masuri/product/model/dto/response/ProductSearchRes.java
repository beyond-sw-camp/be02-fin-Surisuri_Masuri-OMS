package com.example.Surisuri_Masuri.product.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ProductSearchRes {
    String productName;
    Integer price;
    Date createdAt;
    Date updatedAt;
}
