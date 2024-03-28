package com.example.Surisuri_Masuri.container.repository.QueryDsl;

import com.example.Surisuri_Masuri.cart.model.CartDetail;
import com.example.Surisuri_Masuri.container.model.entity.ContainerStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContainerStockRepositoryCustom {
    public Page<ContainerStock> findList(Integer containerIdx, Pageable pageable);
}
