package com.example.Surisuri_Masuri.config;

import com.example.Surisuri_Masuri.jwt.JwtFilter;
import com.example.Surisuri_Masuri.jwt.JwtUtils;
import com.example.Surisuri_Masuri.member.Service.ManagerService;
import com.example.Surisuri_Masuri.member.Service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final UserService userService;
    private final ManagerService managerService;
    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, String> redisTemplate;

    public SecurityConfig(UserService userService, ManagerService managerService, JwtUtils jwtUtils, RedisTemplate<String, String> redisTemplate) {
        this.userService = userService;
        this.managerService = managerService;
        this.jwtUtils = jwtUtils;
        this.redisTemplate = redisTemplate;
    }

    @Value("${jwt.secret-key}")
    private String secretKey;


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {

            http.csrf().disable()
                    .authorizeRequests()
//                    .antMatchers("/user/test").hasAuthority("ROLE_USER")
//                    .antMatchers("/user/update").hasAuthority("ROLE_USER")
//                    .antMatchers("/store/create").hasAuthority("ROLE_MANAGER")
//                    .antMatchers("/store/search").hasAuthority("ROLE_MANAGER")
//                    .antMatchers("/user/register").permitAll()
//                    .antMatchers("/manager/register").permitAll()
//                    .antMatchers("/user/confirm").permitAll()
//                    .antMatchers("/user/findEmail").permitAll()
//                    .antMatchers("/user/login").permitAll()
//                    .antMatchers("/manager/login").permitAll()
//                    .antMatchers("/user/findPassword").permitAll()
//                    .antMatchers("/user/resetPassword/{idx}").permitAll()
//                    .antMatchers("/product/**").permitAll()
//                    .antMatchers("/cart/**").permitAll()
//                    .antMatchers("/orders/**").permitAll()
//                    .antMatchers("/stock/**").permitAll()
//                    .antMatchers("/notice/**").permitAll()
//                    .antMatchers("/question/**").permitAll()
//                    .antMatchers("/store/**").permitAll()
//                    .antMatchers("/container/**").permitAll()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated();
            http.formLogin().disable();
            http.addFilterBefore(new JwtFilter(userService,managerService,redisTemplate,secretKey,jwtUtils), UsernamePasswordAuthenticationFilter.class);
            http.formLogin().disable();
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
