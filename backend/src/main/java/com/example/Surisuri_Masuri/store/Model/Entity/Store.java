package com.example.Surisuri_Masuri.store.Model.Entity;

import com.example.Surisuri_Masuri.cart.model.Cart;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.storeStock.Model.Entity.StoreStock;
import com.example.Surisuri_Masuri.orders.model.Orders;
import com.example.Surisuri_Masuri.orders.model.OrdersDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idx;

    private String storeName;

    private String storeUuid;

    private String storeAddr;

    private String storePhoneNo;

    private Date createdAt;

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
