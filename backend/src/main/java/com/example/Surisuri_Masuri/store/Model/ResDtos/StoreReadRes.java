package com.example.Surisuri_Masuri.store.Model.ResDtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreReadRes {
    private final String storeName;
    private final String storeAddr;
    private final String storeUuid;
    private final String userPhoneNo;
    private final String storePhoneNo;
}
