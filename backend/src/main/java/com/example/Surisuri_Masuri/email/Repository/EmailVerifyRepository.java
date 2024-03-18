package com.example.Surisuri_Masuri.email.Repository;

import com.example.Surisuri_Masuri.email.Model.EmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {
    Optional<EmailVerify> findByEmail(String email);
}
