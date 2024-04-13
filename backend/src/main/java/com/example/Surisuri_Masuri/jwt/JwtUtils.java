package com.example.Surisuri_Masuri.jwt;

import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Service.ManagerServiceImpl;
import com.example.Surisuri_Masuri.member.Service.UserServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtUtils {
    public static final String ACCESS_TOKEN = "AccessToken";
    public static final String REFRESH_TOKEN = "RefreshToken";

    private final ManagerServiceImpl managerServceImpl;

    private final UserServiceImpl userServiceImpl;

    public static String generateAccessToken(User user, String secretkey, int expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("idx", user.getIdx());
        claims.put("userId", user.getUserEmail());
        claims.put("storeUuid", user.getStore().getStoreUuid());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs/100))
                .signWith(getSignKey(secretkey), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public static String generateRefreshToken(User user, String secretkey, int expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("id",user.getUserEmail());
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getSignKey(secretkey), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public static String generateAccessToken(Manager manager, String secretkey, int expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("idx", manager.getIdx());
        claims.put("managerId",manager.getManagerId());
        claims.put("managerEmail", manager.getManagerEmail());
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs/100))
                .signWith(getSignKey(secretkey), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public static String generateRefreshTokenManager(Manager manager, String secretkey, int expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("managerId",manager.getManagerId());
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

    // 가맹점용 액세스 토큰 검증
    public static Boolean validateAccessToken(String accessToken, String key) {
        Date expireTime = extractAllClaims(accessToken, key).getExpiration();
        return expireTime.before(new Date(System.currentTimeMillis()));
    }

    // 가맹점용 리프레시 토큰 검증
    public static Boolean validateRefreshToken(String RefreshToken, String key) {
        Date expireTime = extractAllClaims(RefreshToken, key).getExpiration();
        return expireTime.before(new Date(System.currentTimeMillis()));
    }

    public static String getUserEmail(String token, String key) {
        return  extractAllClaims(token, key).get("userId", String.class);
    }

    public static String getStoreUuid(String token, String key) {
        return extractAllClaims(token, key).get("storeUuid", String.class);
    }

    public static String getManagerInfo(String token, String key) {
        return extractAllClaims(token, key).get("managerId", String.class);
    }

    public static String getManagerId(String token, String key) {
        try {
            // 정상적인 토큰 파싱 시도
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSignKey(key))
                    .build()
                    .parseClaimsJws(token);
            return claimsJws.getBody().get("managerId", String.class);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서 클레임 추출
            return e.getClaims().get("managerId", String.class);
        } catch (Exception e) {
            // 기타 예외 처리
            System.out.println("토큰 파싱 중 오류 발생: " + e.getMessage());
            return null; // 또는 적절한 에러 처리
        }
    }

    public static String getUserId(String token, String key) {
        try {
            // 정상적인 토큰 파싱 시도
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSignKey(key))
                    .build()
                    .parseClaimsJws(token);
            return claimsJws.getBody().get("userId", String.class);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서 클레임 추출
            return e.getClaims().get("userId", String.class);
        } catch (Exception e) {
            // 기타 예외 처리
            System.out.println("토큰 파싱 중 오류 발생: " + e.getMessage());
            return null; // 또는 적절한 에러 처리
        }
    }

    public static Long getIdx(String token, String key) {
        try {
            // 정상적인 토큰 파싱 시도
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSignKey(key))
                    .build()
                    .parseClaimsJws(token);
            return claimsJws.getBody().get("idx", Long.class);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서 클레임 추출
            return e.getClaims().get("idx", Long.class);
        } catch (Exception e) {
            // 기타 예외 처리
            System.out.println("토큰 파싱 중 오류 발생: " + e.getMessage());
            return null; // 또는 적절한 에러 처리
        }
    }

    public static Claims getManagerInfo2(String token, String key) {
        return extractAllClaims(token, key);
    }

    // extractAllclaims() : 토큰이 유효한 토큰인지 검사한 후, 토큰에 담긴 Payload 값을 가져온다.
    public static Claims extractAllClaims(String token, String key) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey(key))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰의 경우, 여기서 Claims를 리턴합니다.
            return e.getClaims();
        }
    }

    public static String replaceToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.split(" ")[1];
        }
        return token;
    }

    // Header 구별하기 Access or Refresh
    public static String getHeaderToken(HttpServletRequest request, String type) {
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);
    }

    // 인증 객체 생성
    public Authentication createManagerAuthentication(String managerId) {
        UserDetails userDetails = managerServceImpl.loadUserByUsername(managerId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public Authentication createUserAuthentication(String userId) {
        UserDetails userDetails = userServiceImpl.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
