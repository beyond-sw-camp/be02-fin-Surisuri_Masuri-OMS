package com.example.Surisuri_Masuri.orders.model;

import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long totalPrice;
    private String merchantUid;
    private String deliveryStatus;
    private String refundReason;
    private String payMethod;

    @Column(updatable = false, nullable = false)
    private Date createdAt;
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "Store_idx")
    private Store store;

    @OneToMany(mappedBy = "orders")
    private List<OrdersDetail> ordersDetailList = new ArrayList<>();

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
