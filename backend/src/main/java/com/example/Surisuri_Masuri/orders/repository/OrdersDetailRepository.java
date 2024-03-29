package com.example.Surisuri_Masuri.orders.repository;

import com.example.Surisuri_Masuri.orders.model.Orders;
import com.example.Surisuri_Masuri.orders.model.OrdersDetail;
import com.example.Surisuri_Masuri.orders.repository.QueryDsl.OrdersDetailRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersDetailRepository extends JpaRepository<OrdersDetail, Long>, OrdersDetailRepositoryCustom {
    public List<OrdersDetail> findByOrdersIdx(Long ordersIdx);
}
