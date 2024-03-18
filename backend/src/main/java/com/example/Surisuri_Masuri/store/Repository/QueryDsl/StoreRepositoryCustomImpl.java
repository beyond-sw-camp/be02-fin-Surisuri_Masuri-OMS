package com.example.Surisuri_Masuri.store.Repository.QueryDsl;

import com.example.Surisuri_Masuri.store.Model.Entity.QStore;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;

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
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch().stream().collect(Collectors.toList());

        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }

}
