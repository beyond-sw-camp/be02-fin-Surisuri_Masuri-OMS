package com.example.Surisuri_Masuri.product.model.dto.request;

import com.example.Surisuri_Masuri.product.model.productEnum.ProductCategory;
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
    Boolean isItFood;
    ProductCategory productCategory;
}
