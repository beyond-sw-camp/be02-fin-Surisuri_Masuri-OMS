package com.example.Surisuri_Masuri.store.Model.ReqDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreCreateReq {
    private String storeUuid;
    private String storeName;
}
