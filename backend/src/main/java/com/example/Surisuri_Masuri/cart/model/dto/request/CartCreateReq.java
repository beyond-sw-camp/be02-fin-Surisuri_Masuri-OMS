package com.example.Surisuri_Masuri.cart.model.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
@Builder
public class CartCreateReq {
    Long productIdx;

    @Min(value = 1, message = "상품 수량 선택은 1이상 가능합니다.")
    Integer productQuantity;
}