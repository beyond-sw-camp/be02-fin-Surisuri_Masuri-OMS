package com.example.Surisuri_Masuri.orders.repository;

import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.orders.model.Orders;
import com.example.Surisuri_Masuri.orders.model.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
