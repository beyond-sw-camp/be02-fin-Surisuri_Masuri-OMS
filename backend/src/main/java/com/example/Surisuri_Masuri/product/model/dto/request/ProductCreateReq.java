package com.example.Surisuri_Masuri.product.model.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateReq {
    String productName;
    Integer price;
    LocalDate exprireAt;
}
