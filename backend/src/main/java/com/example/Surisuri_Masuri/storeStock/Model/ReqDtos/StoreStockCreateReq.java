package com.example.Surisuri_Masuri.storeStock.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreStockCreateReq {

    private Long productIdx;
    private Long stockQuantitiy;

}
