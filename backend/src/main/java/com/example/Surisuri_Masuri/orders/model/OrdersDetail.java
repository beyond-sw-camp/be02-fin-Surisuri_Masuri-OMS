package com.example.Surisuri_Masuri.orders.model;

import com.example.Surisuri_Masuri.product.model.Product;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrdersDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Integer procuctQuantity;

    @Column(updatable = false, nullable = false)
    private Date createdAt;
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "Product_idx")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "Orders_idx")
    private Orders orders;

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
