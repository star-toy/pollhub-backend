package world.startoy.polling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
            "/favicon.ico",
            "/actuator/**"}; // 이미지 파일에 대한 접근 허용

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .requiresChannel(channel -> channel

                       
                .anyRequest().requiresInsecure()) // HTTP 허용             

                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults()); // CORS 설정 추가

        return http.build();
    }

}
