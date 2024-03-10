package com.example.Surisuri_Masuri.product.repository;

import com.example.Surisuri_Masuri.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
