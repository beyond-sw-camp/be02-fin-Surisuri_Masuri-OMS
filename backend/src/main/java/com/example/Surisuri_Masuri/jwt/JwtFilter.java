package com.example.Surisuri_Masuri.jwt;

import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Service.ManagerService;
import com.example.Surisuri_Masuri.member.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    private final ManagerService managerService;

    // Spring Security를 통해 전달 받은 secretKey
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청에 대한 정보가 담긴 Request에서 Header를 추출
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 헤더에 담긴 JWT 토큰을 추출하기 위한 작업, Jwt 토큰은 Bearer로 시작하는 특징을 활용
        String token;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.split(" ")[1];
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        // 이후 사용자를 구별하기위해 Email을 통해 사용자 탐색
        String email = JwtUtils.getUserEmail(token, secretKey);
        Long idx = JwtUtils.getUserIdx(token, secretKey);
        String managerId = JwtUtils.getManagerInfo(token,secretKey);

        // 가맹점 먼저 확인
        User user = userService.getUserByUserEmail(email);

        if (user != null) {
            String userEmail = user.getUserEmail();

            if (!JwtUtils.validate(token, userEmail, secretKey)) {
                filterChain.doFilter(request, response);
                return;
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    User.builder().userEmail(email).idx(idx).build(), null,
                    user.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        }

        // 가맹점이 아니면 본사직원일것이다
        else {
            Manager manager = managerService.getManagerByManagerId(managerId);

            String managerId2 = manager.getUsername();

            if (!JwtUtils.validate2(token, managerId2, secretKey)) {
                filterChain.doFilter(request, response);
                return;
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    Manager.builder().managerEmail(email).idx(idx).build(), null,
                    manager.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        }
    }
}
