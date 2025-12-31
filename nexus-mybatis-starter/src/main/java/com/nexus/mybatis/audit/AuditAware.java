package com.nexus.mybatis.audit;

/**
 * DB 감사(Audit) 사용자 ID 제공 인터페이스
 * (Spring Data 의존성 제거를 위해 자체 정의)
 */
public interface AuditAware {
    String getCurrentAuditor();
}