package com.nexus.web.advice;

import com.nexus.core.api.NexusResponse;
import com.nexus.core.exception.NexusErrorCode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 예상치 못한 모든 시스템 예외 처리 (최후의 방어선)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<NexusResponse<Void>> handleException(Exception e) {
        // 서버 로그에는 진짜 원인을 남깁니다 (디버깅용)
        log.error("[NEXUS-500] Unhandled Exception occurred: ", e);
        
        // 클라이언트에게는 "시스템 에러"라고만 정중히 알립니다 (보안용)
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(NexusResponse.fail(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }

    // --- 내부용 공통 에러 코드 정의 (임시) ---
    // 나중에 에러가 많아지면 별도 파일로 분리합니다.
    private enum CommonErrorCode implements NexusErrorCode {
        INTERNAL_SERVER_ERROR("SYSTEM_ERROR", "일시적인 시스템 오류입니다. 잠시 후 다시 시도해주세요.", 500);

        private final String code;
        private final String message;
        private final int status;

        CommonErrorCode(String code, String message, int status) {
            this.code = code;
            this.message = message;
            this.status = status;
        }

        @Override public String getName() { return code; }
        @Override public String getMessage() { return message; }
        @Override public int getHttpStatus() { return status; }
    }
}