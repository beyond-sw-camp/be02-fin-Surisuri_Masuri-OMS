package com.example.Surisuri_Masuri.cart.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class CartCreateRes {
    public Long idx;
    public String productName;
    public Integer price;
    public Integer productQuantity;
}
