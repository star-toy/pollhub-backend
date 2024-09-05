package com.startoy.pollhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity(debug = true) // 디버깅 활성화
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/api/session/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/api/**",
                                "/error").permitAll()  // Swagger 관련 경로 허용
                        .requestMatchers(HttpMethod.POST, "/api/**").permitAll()  // 모든 /api/** 경로에서 POST 요청 허용
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()
                       // .ignoringRequestMatchers("/api/session/**", "/api/**")  // 특정 경로에 대해 CSRF 보호 비활성화
                )
                .formLogin(form -> form.disable()); // 기본 로그인 폼 비활성화

        return http.build();
    }

}