package com.example.Surisuri_Masuri.jwt.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import javax.persistence.Id;


@AllArgsConstructor
@Builder
@RedisHash(value = "refreshToken", timeToLive = 86400)
public class RefreshToken {

    @Id
    private Long idx;

    @Indexed
    private String userId;

    private String refreshToken;

    public RefreshToken(String userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}