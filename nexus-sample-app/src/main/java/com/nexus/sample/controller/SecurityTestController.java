package com.nexus.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.security.annotation.CurrentUser;
import com.nexus.security.core.JwtTokenProvider;
import com.nexus.security.dto.NexusUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SecurityTestController {

    private final JwtTokenProvider tokenProvider;

    // 1. [로그인 시뮬레이션] 토큰 발급 테스트
    // NexusSecurityProperties 기본값에 의해 /auth/** 는 인증 없이 접근 가능
    @GetMapping("/auth/login") 
    public String fakeLogin() {
        // 가짜 사용자 정보 생성
        NexusUser mockUser = new NexusUser("dev_master", "김넥서스", "ROLE_ADMIN", "DEV001");
        
        // 토큰 생성 (우리가 만든 엔진 사용)
        String token = tokenProvider.createToken(mockUser);
        
        log.info("Fake Login Success. Token generated.");
        return token; // 브라우저에 토큰 문자열 출력
    }

    // 2. [보안 적용 확인] 내 정보 조회
    // @CurrentUser가 정상 작동한다면 user 객체에 데이터가 들어있어야 함
    @GetMapping("/api/me")
    public NexusUser getMyInfo(@CurrentUser NexusUser user) {
        log.info("Authenticated User Accessed: {}", user);
        return user; // JSON으로 사용자 정보 반환
    }
}