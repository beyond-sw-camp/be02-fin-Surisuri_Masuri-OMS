package com.example.Surisuri_Masuri.store.Model.Entity;

import com.example.Surisuri_Masuri.cart.model.Cart;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "userIdx")
    private User user;

    @OneToMany(mappedBy = "store")
    private List<Cart> cartList = new ArrayList<>();

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
