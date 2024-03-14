package com.example.Surisuri_Masuri.product.repository;

import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.model.ProductExpire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductExpireRepository extends JpaRepository<ProductExpire, Long> {
}
