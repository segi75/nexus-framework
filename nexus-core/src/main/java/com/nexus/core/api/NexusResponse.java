package com.nexus.core.api;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

import com.nexus.core.exception.NexusErrorCode;

@Getter
public class NexusResponse<T> {

    private final T data;
    private final Meta meta;
    private final ErrorSpec error;

    // --- 생성자 메서드 (Static Factory Method) ---
    
    // 1. 성공 시
    public static <T> NexusResponse<T> success(T data) {
        return new NexusResponse<>(data, null);
    }

    // 2. 실패 시
    public static NexusResponse<Void> fail(NexusErrorCode errorCode) {
        return new NexusResponse<>(null, new ErrorSpec(errorCode));
    }
    
    // 3. 실패 시 (상세 메시지 포함)
    public static NexusResponse<Void> fail(NexusErrorCode errorCode, String detailMessage) {
        return new NexusResponse<>(null, new ErrorSpec(errorCode.getName(), detailMessage));
    }

    // Private Constructor
    private NexusResponse(T data, ErrorSpec error) {
        this.data = data;
        this.error = error;
        this.meta = new Meta();
    }

    // --- 내부 클래스 정의 ---

    @Getter
    public static class Meta {
        private final String traceId;
        private final LocalDateTime serverTime;

        public Meta() {
            // TODO: 추후 MDC나 ContextHolder에서 TraceId를 꺼내오도록 수정 예정
            this.traceId = UUID.randomUUID().toString(); 
            this.serverTime = LocalDateTime.now();
        }
    }

    @Getter
    public static class ErrorSpec {
        private final String code;
        private final String message;

        public ErrorSpec(NexusErrorCode errorCode) {
            this.code = errorCode.getName();
            this.message = errorCode.getMessage();
        }

        public ErrorSpec(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}