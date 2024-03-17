package com.example.Surisuri_Masuri.member.Model.Entity;

import com.example.Surisuri_Masuri.notice.model.entity.Notice;
import com.example.Surisuri_Masuri.question.model.entity.Answer;
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
public class Manager implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어와 숫자만 가능합니다.")
    private String managerId;

    @Column(nullable = false)
    @Pattern(regexp = "^[가-힣]*$", message = "한글만 가능합니다.")
    private String managerName;

    @Column(nullable = false)
    @Pattern(regexp = "^(\\d{2,3}-?\\d{3,4}-?\\d{4}|\\d{10,11})$", message = "올바른 전화번호 형식이어야 합니다.")
    private String managerPhoneNo;

    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "영어와 한글만 가능합니다.")
    private String department;

    @Column(nullable = false, unique = true)
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String managerEmail;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[A-Z])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{8,15}$", message = "비밀번호는 대문자를 포함한 8~15자의 영문, 숫자, 특수문자(!@#$%^&*()_+-=[])만 가능합니다.")
    private String managerPassword;

    @Column(nullable = false)
    private String managerAuthority;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
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