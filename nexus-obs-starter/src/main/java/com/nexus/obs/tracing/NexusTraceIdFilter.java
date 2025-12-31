package com.nexus.obs.tracing;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE) // 다른 필터보다 제일 먼저 실행
public class NexusTraceIdFilter extends OncePerRequestFilter {

    // 표준 헤더 키 정의 (필요시 'X-Correlation-Id' 등으로 변경 가능)
    public static final String TRACE_ID_HEADER = "X-NEXUS-TRACE-ID";
    public static final String MDC_KEY = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Trace ID 추출 또는 생성
        String traceId = request.getHeader(TRACE_ID_HEADER);
        if (!StringUtils.hasText(traceId)) {
            traceId = UUID.randomUUID().toString().substring(0, 8); // 8자리 숏 UUID
        }

        // 2. MDC(로깅 컨텍스트)에 저장 -> 이제 log.info() 찍으면 자동으로 나옴
        MDC.put(MDC_KEY, traceId);

        // 3. 응답 헤더에도 넣어줌 (클라이언트가 문제 리포트할 때 사용)
        response.setHeader(TRACE_ID_HEADER, traceId);

        try {
            // 4. 다음 필터/컨트롤러 실행
            filterChain.doFilter(request, response);
        } finally {
            // 5. 요청 처리 끝나면 반드시 삭제 (ThreadLocal 메모리 누수 방지)
            MDC.clear();
        }
    }
}