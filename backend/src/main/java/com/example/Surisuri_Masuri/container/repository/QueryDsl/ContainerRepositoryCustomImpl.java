package com.example.Surisuri_Masuri.container.repository.QueryDsl;

import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.cart.model.QCartDetail;
import com.example.Surisuri_Masuri.container.model.entity.Container;
import com.example.Surisuri_Masuri.container.model.entity.QContainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ContainerRepositoryCustomImpl extends QuerydslRepositorySupport implements ContainerRepositoryCustom {

    public ContainerRepositoryCustomImpl() {
        super(Container.class);
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
}
