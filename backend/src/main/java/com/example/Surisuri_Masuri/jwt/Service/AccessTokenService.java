package com.example.Surisuri_Masuri.jwt.Service;

import com.example.Surisuri_Masuri.common.BaseResponse;
import com.example.Surisuri_Masuri.exception.EntityException.ManagerException;
import com.example.Surisuri_Masuri.exception.EntityException.UserException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.jwt.Model.Dto.AccessTokenDto;
import com.example.Surisuri_Masuri.jwt.Model.Dto.TokenDto;
import com.example.Surisuri_Masuri.jwt.Model.RefreshToken;
import com.example.Surisuri_Masuri.jwt.Repository.RefreshTokenRepository;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.store.Model.Entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    public BaseResponse<AccessTokenDto> updateRefreshToken(String accessToken , String refreshToken)
    {

        // 새로운 액세스 토큰을 발급해준다
        String userId = JwtUtils.getUserEmail(accessToken,secretKey);
        String managerId = JwtUtils.getManagerInfo(accessToken,secretKey);

        if(userId != null) {

            String storeUuid = JwtUtils.getStoreUuid(accessToken,secretKey);

            User user = User
                    .builder()
                    .userEmail(userId)
                    .store(Store.builder()
                            .storeUuid(storeUuid)
                            .build())
                    .build();

            String newAccessToken = JwtUtils.generateAccessToken(user, secretKey, expiredTimeMs);

            AccessTokenDto accessTokenDto = AccessTokenDto
                    .builder()
                    .AccessToken(newAccessToken)
                    .build();

            return BaseResponse.successResponse("액세스 토큰이 재발급 되었습니다.", accessTokenDto);
        }

        else {

            Manager manager = Manager
                    .builder()
                    .managerId(managerId)
                    .build();

            String newAccessToken = JwtUtils.generateAccessToken(manager, secretKey, expiredTimeMs);

            AccessTokenDto accessTokenDto = AccessTokenDto
                    .builder()
                    .AccessToken(newAccessToken)
                    .build();
            return BaseResponse.successResponse("액세스 토큰이 재발급 되었습니다.", accessTokenDto);
        }
    }
}