package com.example.Surisuri_Masuri.product.repository.QueryDsl;

import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.product.model.QProduct;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.QStoreStock;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {
    public ProductRepositoryCustomImpl() {
        super(Product.class);
    }
    @Override
    public Page<Product> findList(Pageable pageable) {
        QProduct product= new QProduct("product");

        List<Product> result = from(product)
                .distinct()
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }

}
