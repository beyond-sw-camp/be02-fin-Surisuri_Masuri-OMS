package com.example.Surisuri_Masuri.storeStock.Model.ReqDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreStockSearchReq {

    private String productName;

    @JsonCreator
    public StoreStockSearchReq(String productName) {
        this.productName = productName;
    }
}
