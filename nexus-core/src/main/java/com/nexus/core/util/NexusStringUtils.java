package com.nexus.core.util;

import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * NEXUS 표준 문자열 유틸리티
 * org.springframework.util.StringUtils 기능을 포함하며 추가 기능 제공
 */
public class NexusStringUtils extends StringUtils {

    // 인스턴스화 방지
    private NexusStringUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * 문자열이 null이거나 비어있으면 대체 문자열 반환 (NVL)
     */
    public static String nvl(String str, String defaultStr) {
        return hasText(str) ? str : defaultStr;
    }

    /**
     * UUID 생성 (Hyphen 제거 옵션)
     */
    public static String getUuid(boolean removeHyphen) {
        String uuid = UUID.randomUUID().toString();
        return removeHyphen ? uuid.replace("-", "") : uuid;
    }

    /**
     * 카멜케이스(camelCase) -> 스네이크케이스(snake_case) 변환
     */
    public static String toSnakeCase(String str) {
        if (!hasText(str)) return str;
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return str.replaceAll(regex, replacement).toLowerCase();
    }
    
    /**
     * 스네이크케이스(snake_case) -> 카멜케이스(camelCase) 변환
     */
    public static String toCamelCase(String str) {
        if (!hasText(str)) return str;
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;

        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return result.toString();
    }
}