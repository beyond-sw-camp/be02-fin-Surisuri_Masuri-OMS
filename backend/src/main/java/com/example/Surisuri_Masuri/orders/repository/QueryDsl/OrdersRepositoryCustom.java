package com.example.Surisuri_Masuri.orders.repository.QueryDsl;

import com.example.Surisuri_Masuri.orders.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrdersRepositoryCustom {
    public Page<Orders> findList(Pageable pageable);

    public Page<Orders> findListByUserIdx(Long userIdx, Pageable pageable);
}
