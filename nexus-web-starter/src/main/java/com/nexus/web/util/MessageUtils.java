package com.nexus.web.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * MessageSource 접근을 위한 정적 유틸리티
 * Bean 주입 없이 어디서든 메시지 조회 가능
 */
@Component
@RequiredArgsConstructor
public class MessageUtils {

    private final MessageSource source;
    private static MessageSource messageSource;

    @PostConstruct
    public void init() {
        // Static 필드에 Bean 주입
        messageSource = source;
    }

    /**
     * 코드에 해당하는 메시지 반환 (현재 Locale 기준)
     */
    public static String get(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    /**
     * 파라미터가 있는 메시지 반환
     * 예: valid.required={0}은(는) 필수입니다. -> get("valid.required", "이름")
     */
    public static String get(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}