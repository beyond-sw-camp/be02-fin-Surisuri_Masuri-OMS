package com.example.batch.Repository;

import com.example.batch.entities.ContainerStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ContainerStockRepository extends JpaRepository<ContainerStock, Integer> {

    Page<ContainerStock> findByExpiredAtAndIsDiscardedFalseAndProductIsItFoodTrue(LocalDate expirationDate, Pageable pageable);
}
