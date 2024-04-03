package com.example.Surisuri_Masuri.jwt.Controller;

import com.example.Surisuri_Masuri.jwt.Service.AccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class AccessTokenController {
    private final AccessTokenService accessTokenService;

    @GetMapping("/jwt/reload")
    public ResponseEntity updateRefreshToken(@RequestHeader(value = "AccessToken") String accessToken,
                                             @RequestHeader(value = "RefreshToken") String refreshToken)
    {
        return ResponseEntity.ok().body(accessTokenService.updateRefreshToken(accessToken,refreshToken));
    }

}
