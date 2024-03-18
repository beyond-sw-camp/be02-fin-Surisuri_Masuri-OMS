package com.example.Surisuri_Masuri.container.repository;

import com.example.Surisuri_Masuri.container.model.entity.ContainerStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface ContainerStockRepository extends JpaRepository<ContainerStock, Integer> {
    List<ContainerStock> findByContainerIdx(Integer idx);

    @Query("SELECT cs FROM ContainerStock cs WHERE cs.expiredAt = :expirationDate AND cs.isDiscarded = false AND cs.product.isItFood = true")
    List<ContainerStock> findExpiredFoodProducts(LocalDate expirationDate);
}
