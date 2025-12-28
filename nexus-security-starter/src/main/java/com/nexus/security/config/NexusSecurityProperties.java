package com.nexus.security.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "nexus.security")
public class NexusSecurityProperties {
    
    private boolean enabled = true;
    private boolean useFormLogin = false;

    // 1. [불변] 프레임워크가 제공하는 기본 허용 URL (상수로 관리)
    private static final List<String> DEFAULT_PUBLIC_URLS = List.of(
            "/auth/login", 
            "/auth/refresh", 
            "/h2-console/**", 
            "/favicon.ico", 
            "/error",
            "/swagger-ui/**", 
            "/v3/api-docs/**"
    );

    // 2. [설정] 사용자가 YAML에서 추가할 URL
    private List<String> publicUrls = new ArrayList<>();

    // 3. [설정] 사용자가 YAML에서 제거할 URL (예: 운영계에서 H2 제거)
    private List<String> excludeUrls = new ArrayList<>();

    /**
     * 최종 허용 URL 목록을 계산하여 반환합니다.
     * 로직: (기본값 + 사용자 추가) - 사용자 제외
     */
    public List<String> getFinalPublicUrls() {
        List<String> finalUrls = new ArrayList<>(DEFAULT_PUBLIC_URLS); // 기본값 복사
        
        // 추가
        if (publicUrls != null && !publicUrls.isEmpty()) {
            finalUrls.addAll(publicUrls);
        }
        
        // 제외 (정확히 일치하는 문자열 제거)
        if (excludeUrls != null && !excludeUrls.isEmpty()) {
            finalUrls.removeAll(excludeUrls);
        }
        
        return Collections.unmodifiableList(finalUrls);
    }
}