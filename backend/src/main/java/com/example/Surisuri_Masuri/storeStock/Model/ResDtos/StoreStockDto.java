package com.example.Surisuri_Masuri.storeStock.Model.ResDtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StoreStockDto {
    private String storeName;
    private String productName;
    private LocalDate expiredAt;
    private Long productQuantity;
}
