package com.example.Surisuri_Masuri.container.repository;

import com.example.Surisuri_Masuri.container.model.entity.ContainerStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContainerStockRepository extends JpaRepository<ContainerStock, Integer> {
    List<ContainerStock> findByContainerIdx(Integer idx);
}
