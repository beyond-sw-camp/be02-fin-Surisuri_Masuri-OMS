package com.example.Surisuri_Masuri.storeStock.Repository.QueryDsl;

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
    public Page<StoreStock> findList(Pageable pageable) {
        QStoreStock storeStock= new QStoreStock("storeStock");

        List<StoreStock> result = from(storeStock)
                .where(storeStock.isDiscarded.eq(false))
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());

        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }

}
