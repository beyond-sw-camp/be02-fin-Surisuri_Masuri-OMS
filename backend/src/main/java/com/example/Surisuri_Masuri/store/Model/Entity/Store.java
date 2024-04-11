package com.example.Surisuri_Masuri.store.Model.Entity;

import com.example.Surisuri_Masuri.cart.model.Cart;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import com.example.Surisuri_Masuri.orders.model.Orders;
import com.example.Surisuri_Masuri.orders.model.OrdersDetail;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;

    @Column(nullable = false, length = 100, unique = true)
    private String storeName;

    @Column(nullable = false, length = 100, unique = true)
    private String storeUuid;

    @Column(nullable = true, length = 100, unique = true)
    private String storeAddr;

    @Column(length = 50, unique = true)
    private String storePhoneNo;

    @Column(updatable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user;

    @OneToMany(mappedBy = "store")
    private List<StoreStock> storeStocks;

    @OneToMany(mappedBy = "store")
    private List<Cart> cartList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Orders> ordersList = new ArrayList<>();

    public void setStoreAddr(String storeAddr) {
        this.storeAddr = storeAddr;
    }

    public void setStorePhoneNo(String storePhoneNo) {
        this.storePhoneNo = storePhoneNo;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
