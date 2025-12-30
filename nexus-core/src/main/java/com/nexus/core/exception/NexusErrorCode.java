package com.nexus.core.exception;

/**
 * 모든 커스텀 예외/Enum이 구현해야 하는 최상위 인터페이스
 */
public interface NexusErrorCode {
    String getName();       // 에러 코드 이름 (예: INVALID_INPUT)
    String getMessage();    // 에러 메시지
    int getHttpStatus();    // HTTP 상태 코드 (예: 400, 500)
}