package com.example.Surisuri_Masuri.jwt.Repository;


import com.example.Surisuri_Masuri.jwt.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    public void deleteByIdx(Long userId);

    public void deleteByUserId(String userId);
    Optional<RefreshToken> findByUserId(String userId);
}
