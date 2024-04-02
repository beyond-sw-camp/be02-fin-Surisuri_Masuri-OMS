package com.example.Surisuri_Masuri.storeStock.Repository.QueryDsl;

import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.QStoreStock;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class StoreStockRepositoryCustomImpl extends QuerydslRepositorySupport implements StoreStockRepositoryCustom {

    public StoreStockRepositoryCustomImpl() {
        super(StoreStock.class);
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
}
