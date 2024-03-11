package com.example.Surisuri_Masuri.cart.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class CartCreateRes {
    Long idx;
    String productName;
    Integer price;
    Integer productQuantity;
}
