package com.example.Surisuri_Masuri.product.repository.QueryDsl;

import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    public Page<Product> findList(Pageable pageable);
}
