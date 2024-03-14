package com.example.Surisuri_Masuri.storeStock.Model.ReqDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreStockDeleteReq {
    private Long idx;

    @JsonCreator
    public StoreStockDeleteReq(Long idx) {
        this.idx = idx;
    }
}
