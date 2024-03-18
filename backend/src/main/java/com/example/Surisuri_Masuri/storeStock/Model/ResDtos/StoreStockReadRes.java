package com.example.Surisuri_Masuri.storeStock.Model.ResDtos;

import com.example.Surisuri_Masuri.store.Model.ResDtos.StoreDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StoreStockReadRes {
    private final StoreStockDto storeStockDto;
    private final Long stockQuantitiy;
    private final Boolean discarded;
    private final LocalDate discardedAt;
    private final Long storeStockIdx;

}