package com.example.Surisuri_Masuri.product.model;

import com.example.Surisuri_Masuri.orders.model.OrdersDetail;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductExpire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Date createdAt;
    private Date updatedAt;

    private LocalDate expiredAt;

    @ManyToOne
    @JoinColumn(name = "Product_idx")
    private Product product;

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
