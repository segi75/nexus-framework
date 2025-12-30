package com.nexus.core.security;

import java.util.Optional;

/**
 * [인터페이스] 현재 사용자가 누구인지 확인하는 규격
 */
public interface NexusAuditorAware {
    Optional<String> getCurrentAuditor();
}