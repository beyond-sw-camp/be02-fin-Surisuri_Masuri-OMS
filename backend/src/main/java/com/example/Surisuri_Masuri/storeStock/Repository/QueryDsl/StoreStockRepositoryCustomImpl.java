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

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class StoreStockRepositoryCustomImpl extends QuerydslRepositorySupport implements StoreStockRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final EntityManager em;

    public StoreStockRepositoryCustomImpl(JPAQueryFactory queryFactory, EntityManager em) {
        super(StoreStock.class);
        this.queryFactory = queryFactory;
        this.em = em;
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
    public Long deleteStoreStockByStockIdxAndStoreUuid(Long stockIdx, String storeUuid) {
        // Step 1: 조건에 맞는 StoreStock 조회
        List<StoreStock> storeStocks = queryFactory
                .selectFrom(QStoreStock.storeStock)
                .where(QStoreStock.storeStock.idx.eq(stockIdx)
                        .and(QStoreStock.storeStock.store.storeUuid.eq(storeUuid)))
                .fetch();
        // Step 2: 조회된 StoreStock 삭제
        for (StoreStock storeStock : storeStocks) {
            em.remove(storeStock); // 'em'은 EntityManager의 인스턴스
        }
        // 삭제된 레코드 수 반환
        return (long) storeStocks.size();
    }
}
