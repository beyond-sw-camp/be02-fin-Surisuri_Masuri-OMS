package com.example.batch.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

    @Column(nullable = false)
    private Boolean isItFood;

    @Column(nullable = false)
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    private List<StoreStock> storeStocks;

    @OneToMany(mappedBy = "product")
    private List<OrdersDetail> ordersDetailList;

    @OneToMany(mappedBy = "product")
    private List<ContainerStock> containerStockList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<CartDetail> cartDetailList = new ArrayList<>();

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
