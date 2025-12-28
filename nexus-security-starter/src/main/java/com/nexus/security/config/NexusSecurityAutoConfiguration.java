package com.nexus.security.config;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean; // [필수]
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
    @ConditionalOnMissingBean(SecurityFilterChain.class) // [핵심 2] 개발자가 별도 설정하면 이건 빠져줌 (확장성)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // getPublicUrls() -> getFinalPublicUrls() 호출
        List<String> finalPublicUrls = properties.getFinalPublicUrls();
        log.info("[NEXUS] Security Config Active. Final Public URLs: {}, FormLogin: {}", 
                finalPublicUrls, properties.isUseFormLogin());
        
        http
            .csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())) // H2 콘솔 깨짐 방지
            .httpBasic(AbstractHttpConfigurer::disable);

        // [핵심 1] 설정값에 따른 동적 구성
        if (properties.isUseFormLogin()) {
            // Case A: 폼 로그인 사용 (관리자 페이지 등)
            http.formLogin(form -> form.permitAll());
            
            // 폼 로그인은 세션이 필요하므로 STATELESS 강제를 풂 (기본값: IF_REQUIRED)
            // 별도 설정 안 함 = 필요하면 세션 만듦
        } else {
            // Case B: API 전용 모드 (기본값)
            http.formLogin(AbstractHttpConfigurer::disable);
            http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 끄기
        }
            
        http.authorizeHttpRequests(auth -> auth
        		.requestMatchers(finalPublicUrls.toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
    
    // 2. Argument Resolver 등록
    @Bean
    public WebMvcConfigurer nexusWebMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(new NexusUserArgumentResolver());
            }
        };
    }
}