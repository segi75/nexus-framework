package com.nexusframework.obs.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * [NEXUS Standard]
 * 모든 HTTP 요청의 진입점에서 고유한 Trace ID를 발급합니다.
 * 1. MDC(로깅 컨텍스트)에 넣어서 로그 찍을 때마다 자동으로 나오게 함.
 * 2. Response Header에 넣어서 클라이언트(프론트엔드)가 에러 문의할 때 ID를 알 수 있게 함.
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // 0순위로 실행 (가장 먼저 번호표 발급)
public class TraceIdFilter extends OncePerRequestFilter {

    private static final String TRACE_ID_KEY = "traceId";
    private static final String TRACE_HEADER = "X-Trace-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Trace ID 확인 (없으면 새로 발급)
        String traceId = request.getHeader(TRACE_HEADER);
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString().substring(0, 8); // 간결하게 앞 8자리만 사용
        }

        try {
            // 2. MDC(Logging Context)에 주입 -> 이제부터 log.info() 하면 traceId가 같이 찍힘
            MDC.put(TRACE_ID_KEY, traceId);

            // 3. 응답 헤더에도 탑재 -> 클라이언트가 확인 가능
            response.setHeader(TRACE_HEADER, traceId);

            // 4. 다음 로직(Controller 등) 실행
            filterChain.doFilter(request, response);

        } finally {
            // 5. 요청 처리가 끝나면 스레드 청소 (필수!)
            MDC.clear();
        }
    }
}