package com.example.Surisuri_Masuri.orders.repository.QueryDsl;

import com.example.Surisuri_Masuri.orders.model.Orders;
import com.example.Surisuri_Masuri.orders.model.QOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class OrdersRepositoryCustomImpl extends QuerydslRepositorySupport implements OrdersRepositoryCustom {

    public OrdersRepositoryCustomImpl() {
        super(Orders.class);
    }
    @Override
    public Page<Orders> findList(Pageable pageable) {
        QOrders orders = new QOrders("orders");

        List<Orders> result = from(orders)
                .distinct()
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public Page<Orders> findListByUserIdx(Long userIdx, Pageable pageable) {
        QOrders orders= new QOrders("orders");

        List<Orders> result = from(orders)
                .where(orders.store.user.idx.eq(userIdx))
                .distinct()
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }
}
