package com.example.Surisuri_Masuri.container.repository.QueryDsl;

import com.example.Surisuri_Masuri.container.model.entity.ContainerStock;
import com.example.Surisuri_Masuri.container.model.entity.QContainer;
import com.example.Surisuri_Masuri.container.model.entity.QContainerStock;
import com.example.Surisuri_Masuri.product.model.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
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

    @Override
    public List<LocalDate> findByContainerIdxAndProductName(Integer containerIdx, String productName) {
        QContainerStock containerStock= new QContainerStock("containerStock");
        QContainer container = new QContainer("container");
        QProduct product = new QProduct("product");

        List<LocalDate> result = from(containerStock)
                .select(containerStock.expiredAt)
                .innerJoin(containerStock.container, container)
                .innerJoin(containerStock.product, product)
                .where(container.idx.eq(containerIdx)
                        .and(product.productName.eq(productName))
                        .and(containerStock.isDiscarded.eq(false)))
                .orderBy(containerStock.expiredAt.asc())
                .fetch();

        return result;
    }
}
