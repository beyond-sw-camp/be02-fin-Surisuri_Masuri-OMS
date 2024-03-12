package com.example.Surisuri_Masuri.config;

import com.example.Surisuri_Masuri.jwt.JwtFilter;
import com.example.Surisuri_Masuri.member.Service.ManagerService;
import com.example.Surisuri_Masuri.member.Service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    public SecurityConfig(UserService userService, ManagerService managerService) {
        this.userService = userService;
        this.managerService = managerService;
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
                    .antMatchers("/user/test").hasAuthority("User")
                    .antMatchers("/user/update").hasAuthority("User")
                    .antMatchers("/store/create").hasAuthority("Manager")
                    .antMatchers("/user/register").permitAll()
                    .antMatchers("/manager/register").permitAll()
                    .antMatchers("/user/confirm").permitAll()
                    .antMatchers("/user/findEmail").permitAll()
                    .antMatchers("/user/login").permitAll()
                    .antMatchers("/manager/login").permitAll()
                    .antMatchers("/user/findPassword").permitAll()
                    .antMatchers("/user/resetPassword/{idx}").permitAll()

                    .anyRequest().authenticated();
            http.addFilterBefore(new JwtFilter(userService,managerService,secretKey), UsernamePasswordAuthenticationFilter.class);
            http.formLogin().disable();
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
