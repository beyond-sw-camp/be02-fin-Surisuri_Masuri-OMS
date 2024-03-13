package com.example.Surisuri_Masuri.storeStock.Model.ResDtos;

import com.example.Surisuri_Masuri.store.Model.ResDtos.StoreDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreStockReadRes {
    private final StoreStockDto storeStockDto;
    private final Long stockQuantitiy;
}