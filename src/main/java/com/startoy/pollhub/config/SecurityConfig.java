package com.startoy.pollhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/api/session/**").permitAll()  // 특정 패턴 허용
                        .anyRequest().authenticated()  // 그 외 요청은 인증 필요
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/session/**")  // 특정 경로에 대해 CSRF 보호 비활성화
                )
                .formLogin(form -> form.disable()); // 기본 로그인 폼 비활성화

        return http.build();
    }
}
