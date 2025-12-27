package com.nexus.security.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.nexus.security.annotation.CurrentUser;
import com.nexus.security.dto.NexusUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NexusUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 1. 파라미터에 @CurrentUser 어노테이션이 붙어있고
        // 2. 파라미터 타입이 NexusUser 클래스(또는 하위)인 경우에만 동작
        return parameter.hasParameterAnnotation(CurrentUser.class) 
                && NexusUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        
        // Spring Security Context에서 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 1. 인증 정보가 없으면 null 반환
        if (authentication == null || !authentication.isAuthenticated()) {
            return null; 
        }

        Object principal = authentication.getPrincipal();

        // 2. [Safety Check] 타입 안전성 검증 (A안 방식)
        // 무작정 캐스팅하지 않고, 진짜 NexusUser가 맞는지 확인
        if (principal instanceof NexusUser) {
            return (NexusUser) principal;
        }

        // 타입이 안 맞으면 경고 로그 남기고 null 반환 (에러 방지)
        log.warn("Authentication Principal is not NexusUser. Current type: {}", 
                 principal != null ? principal.getClass().getName() : "null");
        
        return null; 
    }
}