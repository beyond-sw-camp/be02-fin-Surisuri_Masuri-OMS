package com.example.Surisuri_Masuri.container.repository.QueryDsl;

import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.cart.model.QCartDetail;
import com.example.Surisuri_Masuri.container.model.entity.Container;
import com.example.Surisuri_Masuri.container.model.entity.QContainer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ContainerRepositoryCustomImpl extends QuerydslRepositorySupport implements ContainerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ContainerRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(Container.class);
        this.queryFactory = queryFactory;
    }
    @Override
    public Page<Container> findList(Pageable pageable) {
        QContainer container= new QContainer("container");

        List<Container> result = from(container)
                .distinct()
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<Container> findContainerByNameContaining(String name, Pageable pageable) {
        QContainer container = QContainer.container;

        List<Container> containers = queryFactory.selectFrom(container)
                .where(container.containerName.like("%" + name + "%"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(container)
                .where(container.containerName.like("%" + name + "%"))
                .fetchCount();

        return new PageImpl<>(containers, pageable, total);
    }
}
