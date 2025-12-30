package com.nexus.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * NEXUS 표준 날짜 유틸리티
 * Java 21 (java.time) 기반
 */
public class DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDD = "yyyyMMdd";

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD);
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

    private DateUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * 현재 날짜 (yyyy-MM-dd)
     */
    public static String getCurrentDate() {
        return LocalDate.now().format(DEFAULT_DATE_FORMATTER);
    }

    /**
     * 현재 일시 (yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * 포맷 지정 현재 일시
     */
    public static String getCurrentDateTime(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 문자열 -> LocalDate 변환
     */
    public static LocalDate parseDate(String dateStr) {
        return parseDate(dateStr, YYYY_MM_DD);
    }

    public static LocalDate parseDate(String dateStr, String pattern) {
        if (dateStr == null || dateStr.isBlank()) return null;
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 문자열 -> LocalDateTime 변환
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return parseDateTime(dateTimeStr, YYYY_MM_DD_HH_MM_SS);
    }

    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        if (dateTimeStr == null || dateTimeStr.isBlank()) return null;
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 날짜 더하기 (일 단위)
     */
    public static String addDays(String dateStr, long days) {
        LocalDate date = parseDate(dateStr);
        return Optional.ofNullable(date)
                .map(d -> d.plusDays(days).format(DEFAULT_DATE_FORMATTER))
                .orElse(null);
    }
}