package com.example.Surisuri_Masuri.product.repository;

import com.example.Surisuri_Masuri.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findByProductName(String productName);

}
