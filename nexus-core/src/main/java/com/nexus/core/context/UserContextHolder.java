package com.nexus.core.context;

/**
 * ThreadLocal을 사용하여 현재 스레드 내에서만 사용자 정보를 공유
 */
public class UserContextHolder {

    private static final ThreadLocal<UserContext> contextHolder = new ThreadLocal<>();

    // 인스턴스화 방지
    private UserContextHolder() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void setContext(UserContext context) {
        contextHolder.set(context);
    }

    public static UserContext getContext() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
    
    /**
     * 현재 사용자 ID 조회 (없으면 "SYSTEM" 또는 "ANONYMOUS" 반환)
     */
    public static String getUserId() {
        UserContext context = getContext();
        return (context != null && context.getUserId() != null) ? context.getUserId() : "SYSTEM";
    }
}