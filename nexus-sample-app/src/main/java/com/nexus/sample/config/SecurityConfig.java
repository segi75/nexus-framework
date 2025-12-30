package com.nexus.sample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. CSRF 비활성화 (API 테스트 용도)
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. 요청 권한 설정
            .authorizeHttpRequests(auth -> auth
                // '/test/**' 로 시작하는 모든 주소는 로그인 없이 허용
                .requestMatchers("/test/**").permitAll()
                // 그 외 모든 요청도 일단 개발 편의를 위해 허용 (나중에 막을 수 있음)
                .anyRequest().permitAll()
            );

        return http.build();
    }
}