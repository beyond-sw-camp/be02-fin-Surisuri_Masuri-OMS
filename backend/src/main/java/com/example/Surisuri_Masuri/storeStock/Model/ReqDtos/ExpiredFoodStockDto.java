package com.example.Surisuri_Masuri.storeStock.Model.ReqDtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ExpiredFoodStockDto {
    private String productName;
    private LocalDate expiredAt;
    private Long stockQuantity;
    private Boolean discarded;
    private LocalDate discardedAt;
}
