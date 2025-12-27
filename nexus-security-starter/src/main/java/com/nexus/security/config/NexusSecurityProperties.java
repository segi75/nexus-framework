package com.nexus.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "nexus.security")
public class NexusSecurityProperties {
    
    // 사용자가 보안을 끄고 싶을 때 (nexus.security.enabled=false)
    private boolean enabled = true;

    // 인증 없이 접근 가능한 URL 목록 (기본값 설정)
    private List<String> publicUrls = new ArrayList<>(List.of(
            "/auth/login", 
            "/auth/refresh", 
            "/h2-console/**", 
            "/favicon.ico", 
            "/error",
            "/swagger-ui/**", 
            "/v3/api-docs/**"
    ));
}