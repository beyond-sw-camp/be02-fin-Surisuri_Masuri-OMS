package com.example.Surisuri_Masuri.store.Repository.QueryDsl;

import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {
    public Page<Store> findList(Pageable pageable);
}
