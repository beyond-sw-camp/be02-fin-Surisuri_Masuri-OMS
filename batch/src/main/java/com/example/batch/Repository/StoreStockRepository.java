package com.example.batch.Repository;

import com.example.batch.entities.StoreStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StoreStockRepository extends JpaRepository<StoreStock,Long> {

    Page<StoreStock> findByExpiredAtAndIsDiscardedFalseAndProductIsItFoodTrue(LocalDate expirationDate, Pageable pageable);
}