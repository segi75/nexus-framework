package com.nexus.security.core;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * [Internal] 모든 요청을 가로채서 JWT 토큰을 확인하는 필터
 * - 유효한 토큰이면 SecurityContext에 인증 정보를 저장함.
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = resolveToken(request);

            // 토큰이 있고 유효하다면 -> 인증 정보(Authentication) 생성 후 Context에 저장
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.trace("Authentication set for user: {}", auth.getName());
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
            // 여기서 예외를 던지지 않고 다음 필터로 넘김 (Security가 알아서 401 처리)
        }

        filterChain.doFilter(request, response);
    }

    // Request Header에서 "Bearer {token}" 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}