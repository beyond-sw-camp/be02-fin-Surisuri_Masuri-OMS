package com.example.Surisuri_Masuri.member.Model.Entity;

import com.example.Surisuri_Masuri.notice.model.entity.Notice;
import com.example.Surisuri_Masuri.question.model.entity.Answer;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manager implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String managerId;

    private String managerPassword;

    private String managerEmail;

    private String managerName;

    private String managerPhoneNo;

    private String managerAuthority;

    private String department;

    private Date createdAt;

    private Date updatedAt;

    @OneToMany(mappedBy = "manager")
    private List<Answer> answerList = new ArrayList<>();

    @OneToMany(mappedBy = "manager")
    private List<Notice> noticeList = new ArrayList<>();

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setManagerAuthority(String authority) {
        this.managerAuthority = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> managerAuthority);
    }

    @Override
    public String getUsername() {
        return managerId;
    }

    @Override
    public String getPassword()
    {
        return managerPassword;
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