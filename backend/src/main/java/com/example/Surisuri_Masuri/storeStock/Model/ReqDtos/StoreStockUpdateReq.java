package com.example.Surisuri_Masuri.storeStock.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreStockUpdateReq {
    private Long idx;
    private Long stockQuantity;
}
