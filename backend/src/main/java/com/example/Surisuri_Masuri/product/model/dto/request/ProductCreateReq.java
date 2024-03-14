package com.example.Surisuri_Masuri.product.model.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateReq {
    String productName;
    Integer price;
}
