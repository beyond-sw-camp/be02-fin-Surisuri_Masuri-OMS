package com.example.Surisuri_Masuri.jwt;

import com.example.Surisuri_Masuri.exception.EntityException.UserException;
import com.example.Surisuri_Masuri.exception.ErrorCode;
import com.example.Surisuri_Masuri.member.Model.Entity.Manager;
import com.example.Surisuri_Masuri.member.Model.Entity.User;
import com.example.Surisuri_Masuri.member.Service.ManagerService;
import com.example.Surisuri_Masuri.member.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserService userService;

    private final ManagerService managerService;

    private final RedisTemplate<String, String> redisTemplate;

    // Spring Security를 통해 전달 받은 secretKey
    private final String secretKey;

    private final JwtUtils jwtUtils;

    @Value("${jwt.token.expired-time-ms}")
    private int expiredTimeMs;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = JwtUtils.getHeaderToken(request, "Access");
        String refreshToken = JwtUtils.getHeaderToken(request, "Refresh");


        if (accessToken == null)
        // 다음 필터로 넘어간다
        {
            filterChain.doFilter(request, response);
            return;
        } else if (accessToken != null && refreshToken == null) {

            String email = null;
            String managerId = null;

            if (!JwtUtils.validateAccessToken(accessToken, secretKey)) {

                email = JwtUtils.getUserId(accessToken, secretKey);
                managerId = JwtUtils.getManagerId(accessToken, secretKey);


                User user = userService.getUserByUserEmail(email);
                Manager manager = managerService.getManagerByManagerId(managerId);

                if (user != null) {
                    setUserAuthentication(JwtUtils.getUserEmail(accessToken, secretKey));
                    filterChain.doFilter(request, response);
                    return;

                } else if (manager != null) {
                    setManagerAuthentication(JwtUtils.getManagerInfo(accessToken, secretKey));
                    filterChain.doFilter(request, response);
                    return;
                }

            }

            else {
                    // 토큰이 만료되었거나 유효하지 않은 경우
                    try {
                        throw new UserException(ErrorCode.INVALID_AccessTOKEN, "만료된 토큰입니다");
                    } catch (UserException e) {
                        // 여기서 적절한 에러 처리를 수행합니다.
                        // 예: 사용자에게 에러 메시지를 JSON 형태로 응답하기
                        // 응답 상태 코드 설정 (예: HttpServletResponse.SC_UNAUTHORIZED)
                        response.setHeader("Access-Control-Allow-Origin", "*"); // 실제 운영 환경에서는 구체적인 출처를 지정하는 것이 좋습니다.
                        response.setHeader("Access-Control-Expose-Headers", "*, Authorization");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\n    \"error\": \"" + e.getMessage() + "\",\n    \"code\": \"" + (ErrorCode.INVALID_AccessTOKEN.getCode()) + "\"\n}");
                    }
            }

        } else if (accessToken != null && refreshToken != null) {

            String email = null;
            String managerId = null;
            Long userIdx = null;
            // 만약 액세스 토큰이 유효하지 않는다면
            if (JwtUtils.validateAccessToken(accessToken, secretKey))
            // 해당 액세스 토큰을 통해 사용자의 정보를 조회하고
            // 조회된 사용자의 정보를 통해 리프레쉬 토큰을 찾는다
            // 만약 찾은 리프레시 토큰과 전달 받은 리프레쉬 토큰이 같고 && 아직 유효한 토큰이라면
            // 새로운 액세스 토큰을 발급해준다 아니라면 로그아웃 신호를 보내 강제로 로그아웃 시키고 리프레시 토큰을 삭제한다.
            {
                email = JwtUtils.getUserId(accessToken, secretKey);
                managerId = JwtUtils.getManagerId(accessToken, secretKey);

                if (email != null) {
//                    Optional<RefreshToken> refreshTokenById = refreshTokenRepository.findByUserId(email);
                    ValueOperations<String,String> vop = redisTemplate.opsForValue();

                    String resfreshTokenById = vop.get(email);

                    if (resfreshTokenById != null && resfreshTokenById.equals(refreshToken) &&
                            !JwtUtils.validateRefreshToken(refreshToken, secretKey)) {
                        filterChain.doFilter(request, response);
                        return;
                    } else {
                        // 토큰이 만료되었거나 유효하지 않은 경우
                        try {
//                            refreshTokenRepository.deleteByUserId(email);
                            throw new UserException(ErrorCode.INVALID_RefreshTOKEN, "로그아웃하세요");
                        } catch (UserException e) {
                            response.setHeader("Access-Control-Allow-Origin", "*"); // 실제 운영 환경에서는 구체적인 출처를 지정하는 것이 좋습니다.
                            response.setHeader("Access-Control-Expose-Headers", "*, Authorization");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write("{\n    \"error\": \"" + e.getMessage() + "\",\n    \"code\": \"" + ErrorCode.INVALID_RefreshTOKEN.getCode() + "\"\n}");
                        }
                    }
                } else if (managerId != null) {
                    ValueOperations<String,String> vop = redisTemplate.opsForValue();

                    String resfreshTokenById = vop.get(managerId);

                    if (resfreshTokenById != null && resfreshTokenById.equals(refreshToken) &&
                            !JwtUtils.validateRefreshToken(refreshToken, secretKey)) {
                        filterChain.doFilter(request, response);
                        return;
                    } else {
                        // 토큰이 만료되었거나 유효하지 않은 경우
                        try {
                            throw new UserException(ErrorCode.INVALID_RefreshTOKEN, "로그아웃하세요");
                        } catch (UserException e) {
                            response.setHeader("Access-Control-Allow-Origin", "*"); // 실제 운영 환경에서는 구체적인 출처를 지정하는 것이 좋습니다.
                            response.setHeader("Access-Control-Expose-Headers", "*, Authorization");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write("{\n    \"error\": \"" + e.getMessage() + "\",\n    \"code\": \"" + ErrorCode.INVALID_RefreshTOKEN.getCode() + "\"\n}");
                        }
                    }
                }
            }
        }
    }
    public void setManagerAuthentication(String id) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtils.createManagerAuthentication(id);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void setUserAuthentication(String id) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtils.createUserAuthentication(id);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
