package com.example.Surisuri_Masuri.storeStock.Model.ResDtos;

import com.example.Surisuri_Masuri.store.Model.ResDtos.StoreDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreStockCreateRes {

    private StoreDto storeDto;
    private Long productIdx;
    private Long stockQuantitiy;

}
