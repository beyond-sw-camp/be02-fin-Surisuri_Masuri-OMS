package com.example.Surisuri_Masuri.storeStock.Model.ResDtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StoreStockDto {
    private String productName;
    private Long productQuantity;
}
