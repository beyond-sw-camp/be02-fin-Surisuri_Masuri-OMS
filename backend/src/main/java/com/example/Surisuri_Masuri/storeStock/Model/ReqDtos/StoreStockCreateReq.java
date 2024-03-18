package com.example.Surisuri_Masuri.storeStock.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StoreStockCreateReq {

    private Long productIdx;
    private Long stockQuantity;
    private LocalDate expiredAt;

}
