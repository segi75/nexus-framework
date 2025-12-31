package com.nexus.mybatis.interceptor;

import java.util.Optional;

public interface NexusAuditorAware {
    // 현재 사용자 ID를 리턴 (없을 수도 있으므로 Optional)
    Optional<String> getCurrentAuditor();
}