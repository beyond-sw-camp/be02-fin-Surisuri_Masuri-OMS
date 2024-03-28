package com.example.Surisuri_Masuri.container.repository.QueryDsl;

import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.cart.model.QCartDetail;
import com.example.Surisuri_Masuri.container.model.entity.ContainerStock;
import com.example.Surisuri_Masuri.container.model.entity.QContainerStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ContainerStockRepositoryCustomImpl extends QuerydslRepositorySupport implements ContainerStockRepositoryCustom {

    public ContainerStockRepositoryCustomImpl() {
        super(ContainerStock.class);
    }
    @Override
    public Page<ContainerStock> findList(Integer containerIdx, Pageable pageable) {
        QContainerStock containerStock= new QContainerStock("containerStock");

        List<ContainerStock> result = from(containerStock)
                .where(containerStock.container.idx.eq(containerIdx))
                .distinct()
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }
}
