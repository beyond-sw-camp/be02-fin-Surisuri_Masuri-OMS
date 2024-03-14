package com.example.Surisuri_Masuri.storeStock.Model.ResDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreStockSearchRes {
    private StoreStockDto storeStockDto;
    private Long stockQuantity;
    private String storeAddr;
}
