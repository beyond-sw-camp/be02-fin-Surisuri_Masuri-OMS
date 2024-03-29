package com.example.Surisuri_Masuri.orders.repository.QueryDsl;

import com.example.Surisuri_Masuri.orders.model.OrdersDetail;
import com.example.Surisuri_Masuri.orders.model.QOrdersDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class OrdersDetailRepositoryCustomImpl extends QuerydslRepositorySupport implements OrdersDetailRepositoryCustom {

    public OrdersDetailRepositoryCustomImpl() {
        super(OrdersDetail.class);
    }
    @Override
    public Page<OrdersDetail> findListByOrdersIdx(Long ordersIdx, Pageable pageable) {
        QOrdersDetail ordersDetail= new QOrdersDetail("ordersDetail");

        List<OrdersDetail> result = from(ordersDetail)
                .where(ordersDetail.orders.idx.eq(ordersIdx))
                .distinct()
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, result.size());
    }
}
