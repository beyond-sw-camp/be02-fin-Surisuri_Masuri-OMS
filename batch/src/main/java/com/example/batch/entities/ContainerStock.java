package com.example.batch.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(nullable = false, length = 300)
    private Long productQuantity;

    @Column(nullable = false)
    private LocalDate expiredAt;

    @Column(nullable = false)
    private Boolean isDiscarded; // 폐기 여부

    private Timestamp discardedAt; // 폐기 일자

    @ManyToOne
    @JoinColumn(name = "product_Idx")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "container_Idx")
    private Container container;

    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

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

