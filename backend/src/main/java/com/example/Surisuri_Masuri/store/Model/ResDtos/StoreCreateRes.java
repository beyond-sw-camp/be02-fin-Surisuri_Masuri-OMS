package com.example.Surisuri_Masuri.store.Model.ResDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreCreateRes {
    private String storeUuid;
    private String storeName;
}
