package com.example.Surisuri_Masuri.member.Model.Entity;

import com.example.Surisuri_Masuri.notice.model.entity.Notice;
import com.example.Surisuri_Masuri.question.model.entity.Question;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, unique = true)
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String userEmail;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[A-Z])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{8,15}$", message = "비밀번호는 대문자를 포함한 8~15자의 영문, 숫자, 특수문자(!@#$%^&*()_+-=[])만 가능합니다.")
    private String userPassword;

    @Column(nullable = false)
    @Pattern(regexp = "^[가-힣]*$", message = "한글만 가능합니다.")
    private String userName;

    @Column(nullable = false)
    @Pattern(regexp = "^(\\d{2,3}-?\\d{3,4}-?\\d{4}|\\d{10,11})$", message = "올바른 전화번호 형식이어야 합니다.")
    private String userPhoneNo;

    @Column(nullable = false, length = 1)
    private Boolean status;

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    @Column(nullable = false)
    private String userAuthority;

    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Question> questionList = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Store store;

    @OneToMany(mappedBy = "user")
    private List<Notice> noticeList = new ArrayList<>();

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
        return status;
    }

}