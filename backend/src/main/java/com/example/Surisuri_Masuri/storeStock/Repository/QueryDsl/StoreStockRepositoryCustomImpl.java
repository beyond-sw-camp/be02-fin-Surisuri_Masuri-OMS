package com.example.Surisuri_Masuri.storeStock.Repository.QueryDsl;

import com.example.Surisuri_Masuri.storeStock.Model.Entity.QStoreStock;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class StoreStockRepositoryCustomImpl extends QuerydslRepositorySupport implements StoreStockRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StoreStockRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(StoreStock.class);
        this.queryFactory = queryFactory;
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public Long deleteStoreStockByProductIdxAndStoreUuid(Long productIdx, String storeUuid) {
        QStoreStock storeStock = new QStoreStock("storeStock");
        // 실제 삭제 수행
        // 실제 삭제 수행
        Long deletedCount = queryFactory
                .delete(storeStock)
                .where(storeStock.product.idx.eq(productIdx)
                        .and(storeStock.store.storeUuid.eq(storeUuid)))
                .execute();

        return deletedCount; // 삭제된 레코드 수 반환
    }
}
