package com.example.Surisuri_Masuri.cart.model.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartCreateReq {
    Long idx;
    Long productIdx;
    Integer productQuantity;
}
