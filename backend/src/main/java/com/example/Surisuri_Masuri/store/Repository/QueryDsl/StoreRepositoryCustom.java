package com.example.Surisuri_Masuri.store.Repository.QueryDsl;

import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import com.example.Surisuri_Masuri.store.Model.ReqDtos.StoreSearchReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryCustom {
    public Page<Store> findList(Pageable pageable);
    public Page<Store> findStoreByName(String name, Pageable pageable);
}
