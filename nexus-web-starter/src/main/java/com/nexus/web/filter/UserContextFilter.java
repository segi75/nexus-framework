package com.nexus.web.filter;

import com.nexus.core.context.UserContext;
import com.nexus.core.context.UserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class UserContextFilter extends OncePerRequestFilter {

    private static final String HEADER_USER_ID = "X-USER-ID";
    private static final String HEADER_USER_NAME = "X-USER-NAME";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            // 헤더에서 사용자 정보 추출
            String userId = request.getHeader(HEADER_USER_ID);
            String userName = request.getHeader(HEADER_USER_NAME);

            if (userId != null) {
                // 한글 이름 깨짐 방지를 위한 디코딩
                if (userName != null) {
                    userName = URLDecoder.decode(userName, StandardCharsets.UTF_8);
                }

                UserContext context = UserContext.builder()
                        .userId(userId)
                        .userName(userName)
                        .build();

                UserContextHolder.setContext(context);
                log.debug("User Context Set: {}", context);
            }
            
            filterChain.doFilter(request, response);
            
        } finally {
            // [중요] 스레드 풀 재사용 시 데이터 오염 방지를 위해 반드시 초기화
            UserContextHolder.clear();
        }
    }
}