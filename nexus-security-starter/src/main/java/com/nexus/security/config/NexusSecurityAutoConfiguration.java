package com.nexus.security.config;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nexus.security.core.JwtAuthenticationFilter;
import com.nexus.security.core.JwtTokenProvider;
import com.nexus.security.resolver.NexusUserArgumentResolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfiguration
@EnableWebSecurity
@EnableConfigurationProperties(NexusSecurityProperties.class)
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "nexus.security", name = "enabled", havingValue = "true", matchIfMissing = true)
public class NexusSecurityAutoConfiguration {

    private final NexusSecurityProperties properties;
    private final JwtTokenProvider tokenProvider;

    // 1. Spring Security Filter Chain 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("[NEXUS] Security Auto-Configuration Enabled. Public URLs: {}", properties.getPublicUrls());

        http
            .csrf(AbstractHttpConfigurer::disable) // REST API라 불필요
            .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 미사용
            .httpBasic(AbstractHttpConfigurer::disable)
            
            // 세션 정책: STATELESS (JWT 사용)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // URL별 권한 관리
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(properties.getPublicUrls().toArray(new String[0])).permitAll() // 설정된 Public URL 허용
                .anyRequest().authenticated() // 나머지는 인증 필수
            )
            
            // JWT 필터 추가 (UsernamePasswordAuthenticationFilter 앞에 배치)
            .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
    
    // 2. Argument Resolver 등록 (WebMvc)
    @Bean
    public WebMvcConfigurer nexusWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(new NexusUserArgumentResolver());
                log.debug("[NEXUS] NexusUserArgumentResolver registered.");
            }
        };
    }
}