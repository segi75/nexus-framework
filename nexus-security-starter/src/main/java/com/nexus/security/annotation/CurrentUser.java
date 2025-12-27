package com.nexus.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

/**
 * [NEXUS Standard] 현재 로그인한 사용자 정보 주입
 * 사용법: public ResponseEntity<?> logic(@CurrentUser NexusUser user)
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal // Spring Security가 Principal을 주입하도록 유도 (내부적 연결)
public @interface CurrentUser {
    // 향후 확장 가능 (예: boolean required() default true;)
}