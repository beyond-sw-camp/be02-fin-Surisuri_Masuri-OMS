package com.example.Surisuri_Masuri.storeStock.Model.ResDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreStockUpdateRes {
    private String productName;
    private Long stockQuantity;
}
