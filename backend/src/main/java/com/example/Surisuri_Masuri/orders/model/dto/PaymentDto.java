package com.example.Surisuri_Masuri.orders.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PaymentDto {
    private List<CartDto> cartDtoList;
    private int amount;
}
