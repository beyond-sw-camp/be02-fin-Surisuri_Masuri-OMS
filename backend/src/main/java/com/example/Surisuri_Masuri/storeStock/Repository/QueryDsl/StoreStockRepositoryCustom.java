package com.example.Surisuri_Masuri.storeStock.Repository.QueryDsl;

import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreStockRepositoryCustom {
    public Page<StoreStock> findList(Pageable pageable);
}
