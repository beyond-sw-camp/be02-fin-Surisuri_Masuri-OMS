package com.example.Surisuri_Masuri.store.Repository.QueryDsl;

import com.example.Surisuri_Masuri.store.Model.Entity.QStore;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;

import com.example.Surisuri_Masuri.store.Model.ReqDtos.StoreSearchReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class StoreRepositoryCustomImpl extends QuerydslRepositorySupport implements StoreRepositoryCustom{

    public StoreRepositoryCustomImpl() {
        super(Store.class);
    }
    @Override
    public Page<Store> findList(Pageable pageable) {
        QStore store= new QStore("store");

        List<Store> result = from(store)
                .where(store.storeAddr.isNotNull())
                .distinct()
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<Store> findStoreByName(String name, Pageable pageable) {
        QStore store = QStore.store;

        List<Store> result = from(store)
                .where(store.storeName.containsIgnoreCase(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = from(store)
                .where(store.storeName.containsIgnoreCase(name))
                .fetchCount();

        return new PageImpl<>(result, pageable, total);
    }
}
