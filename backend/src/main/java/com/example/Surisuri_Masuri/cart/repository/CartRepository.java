package com.example.Surisuri_Masuri.cart.repository;

import com.example.Surisuri_Masuri.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
