package com.nexus.mybatis.audit;

import com.nexus.core.context.UserContextHolder;
import org.springframework.stereotype.Component;

/**
 * AuditAware 구현체
 * UserContextHolder에서 현재 요청한 사용자 ID를 가져옴
 */
@Component
public class NexusAuditorAware implements AuditAware {

    @Override
    public String getCurrentAuditor() {
        // UserContextHolder.getUserId()는 없으면 "SYSTEM"을 반환하도록 되어 있음
        return UserContextHolder.getUserId();
    }
}