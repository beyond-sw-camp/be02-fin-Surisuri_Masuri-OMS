package com.example.Surisuri_Masuri.member.Model.Entity;

import com.example.Surisuri_Masuri.common.Address;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import lombok.*;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String userEmail;

    private String userPassword;

    private String userName;

    private String userPhoneNo;

    private Boolean status;

    private String userAuthority;

    private Date createdAt;

    private Date updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Store> stores;

    public Boolean changeStatus(Boolean status) {
        return this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> userAuthority);
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public String getPassword()
    {
        return userPassword;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}