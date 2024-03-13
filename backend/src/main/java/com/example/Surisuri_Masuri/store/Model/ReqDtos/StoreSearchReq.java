package com.example.Surisuri_Masuri.store.Model.ReqDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class StoreSearchReq {
    private String storeName;
    @JsonCreator
    public StoreSearchReq(String storeName) {
        this.storeName = storeName;
    }
}
