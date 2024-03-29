package com.example.Surisuri_Masuri.container.repository.QueryDsl;

import com.example.Surisuri_Masuri.container.model.entity.ContainerStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ContainerStockRepositoryCustom {
    public Page<ContainerStock> findList(Integer containerIdx, Pageable pageable);

    public List<LocalDate> findByContainerIdxAndProductName(Integer containerIdx, String productName);
}
