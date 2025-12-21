package com.nexusframework.core.context;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * NEXUS 표준 요청 컨텍스트
 * - 모든 요청(Web, Batch, Event)에서 "누가(User)", "어디서(Tenant)", "무엇을(Trace)" 하는지 추적하는 기준입니다.
 */
@Getter
@ToString
@Builder
public class NexusRequestContext {

    // 1. 추적 ID (필수) - 분산 환경에서 요청 흐름을 연결하는 고리
    private final String traceId;

    // 2. 멀티 테넌트/기관 ID (선택)
    private final String tenantId;

    // 3. 사용자 ID (내부 식별자)
    private final String userId;

    // 4. 사용자 역할 (Role)
    private final String roles;

    // 5. 클라이언트 IP
    private final String clientIp;

    // 6. 확장 속성 (추가 정보를 담을 주머니)
    @Builder.Default
    private final Map<String, Object> attributes = new HashMap<>();

    /**
     * 비어 있는 컨텍스트 생성 (시스템 내부 동작 등에서 사용)
     */
    public static NexusRequestContext empty() {
        return NexusRequestContext.builder()
                .traceId("SYSTEM")
                .userId("SYSTEM")
                .build();
    }
}