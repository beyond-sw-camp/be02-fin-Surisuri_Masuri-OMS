package com.example.Surisuri_Masuri.orders.repository.QueryDsl;

import com.example.Surisuri_Masuri.orders.model.OrdersDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrdersDetailRepositoryCustom {
    public Page<OrdersDetail> findListByOrdersIdx(Long ordersIdx, Pageable pageable);
}
