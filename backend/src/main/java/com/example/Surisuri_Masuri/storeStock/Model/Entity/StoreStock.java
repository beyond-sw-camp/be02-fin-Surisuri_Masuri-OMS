package com.example.Surisuri_Masuri.storeStock.Model.Entity;

import com.example.Surisuri_Masuri.product.model.Product;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long stockQuantitiy;

    private LocalDate expiredAt;

    private Date createdAt;
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeIdx")
    private Store store;

    public void setStockQuantitiy(Long stockQuantitiy) {
        this.stockQuantitiy = stockQuantitiy;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productIdx")
    private Product product;
}
