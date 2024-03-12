package com.example.Surisuri_Masuri.cart.repository;

import com.example.Surisuri_Masuri.cart.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    public List<CartDetail> findByCartIdx(Long cartIdx);
}
