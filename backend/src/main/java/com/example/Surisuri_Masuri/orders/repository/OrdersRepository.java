package com.example.Surisuri_Masuri.orders.repository;

import com.example.Surisuri_Masuri.orders.model.Orders;
import com.example.Surisuri_Masuri.orders.repository.QueryDsl.OrdersRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long>, OrdersRepositoryCustom {
}
