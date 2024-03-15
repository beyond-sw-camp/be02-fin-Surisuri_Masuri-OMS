package com.example.Surisuri_Masuri.product.model;

import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import com.example.Surisuri_Masuri.orders.model.OrdersDetail;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String productName;

    private Integer price;

    @Column(updatable = false, nullable = false)
    private Date createdAt;
    private Date updatedAt;

    private Boolean isItFood;

    @OneToMany(mappedBy = "product")
    private List<StoreStock> storeStocks;

    @OneToMany(mappedBy = "product")
    private List<OrdersDetail> ordersDetailList;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
