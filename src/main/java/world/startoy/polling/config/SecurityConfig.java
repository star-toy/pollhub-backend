package world.startoy.polling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true) // 디버깅 활성화
public class SecurityConfig {

    private static final String[] PUBLIC_URLS = {"/", "/api/session/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/v*/**", // v1, v2, v3 ...
            "/error",
            "/images/**",
            "/favicon.ico"}; // 이미지 파일에 대한 접근 허용

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_URLS).permitAll() // 공개된 URL 패턴을 설정하고, 모든 요청을 허용
                        .requestMatchers(HttpMethod.POST, "/api/**").permitAll()  // /api/** 경로에 대한 POST 요청을 모두 허용
                        .anyRequest().authenticated()
                )
                .requiresChannel(channel -> channel.anyRequest().requiresInsecure()) // HTTPS 강제 리다이렉트 설정 해제
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
                .formLogin(AbstractHttpConfigurer::disable); // 기본 로그인 폼 비활성화

        return http.build();
    }

}