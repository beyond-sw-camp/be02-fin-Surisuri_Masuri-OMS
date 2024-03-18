package com.example.Surisuri_Masuri.jwt;

import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtils {
    // 오버로딩을 활용한 User, Manager의  AccessToken 메소드 분리하여 구현
    // SecretKey는 Spring Security에서 사용되는 JWT의 Key

    public static String generateAccessToken(User user, String secretkey, int expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("idx", user.getIdx());
        claims.put("email", user.getUserEmail());
        claims.put("storeUuid", user.getStore().getStoreUuid());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs*1000L))
                .signWith(getSignKey(secretkey), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static String generateAccessToken(Manager manager, String secretkey, int expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("idx", manager.getIdx());
        claims.put("id",manager.getManagerId());
        claims.put("email", manager.getManagerEmail());
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getSignKey(secretkey), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static Key getSignKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public static Boolean validate(String token, String userEmail, String key) {

        String usernameByToken = getUserEmail(token, key);

        Date expireTime = extractAllClaims(token, key).getExpiration();
        Boolean result = expireTime.before(new Date(System.currentTimeMillis()));

        return usernameByToken.equals(userEmail) && !result;
    }

    // 본사 관리자 계정 JWT 검증 메소드
    public static Boolean validate2(String token, String managerId, String key) {

        String usernameByToken = getManagerInfo(token, key);

        Date expireTime = extractAllClaims(token, key).getExpiration();
        Boolean result = expireTime.before(new Date(System.currentTimeMillis()));

        return usernameByToken.equals(managerId) && !result;
    }

    public static Long getUserIdx(String token, String key) {
        return extractAllClaims(token, key).get("idx", Long.class);
    }

    public static String getUserEmail(String token, String key) {
        return extractAllClaims(token, key).get("email", String.class);
    }

    public static String getStoreUuid(String token, String key) {
        return extractAllClaims(token, key).get("storeUuid", String.class);
    }

    public static String getManagerInfo(String token, String key) {
        return extractAllClaims(token, key).get("id", String.class);
    }

    public static Claims getManagerInfo2(String token, String key) {
        return extractAllClaims(token, key);
    }

    // extractAllclaims() : 토큰이 유효한 토큰인지 검사한 후, 토큰에 담긴 Payload 값을 가져온다.
    public static Claims extractAllClaims(String token, String key) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String replaceToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.split(" ")[1];
        }

        return token;
    }
}
