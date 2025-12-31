package com.nexus.sample.controller;

import com.nexus.core.context.UserContextHolder;
import com.nexus.web.util.MessageUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/framework")
public class FrameworkTestController {

    /**
     * 1. i18n 메시지 유틸 테스트
     * "성공적으로 처리되었습니다."가 리턴되어야 함.
     */
    @GetMapping("/message")
    public String testMessage() {
        // MessageUtils가 nexus-web-starter에 의해 잘 초기화되었는지 확인
        return MessageUtils.get("common.success");
    }

    /**
     * 2. UserContextFilter & Holder 테스트
     * 헤더에 X-USER-ID를 넣으면 그 값이, 안 넣으면 "SYSTEM"이 나와야 함.
     */
    @GetMapping("/context")
    public String testContext() {
        // Filter가 요청을 가로채서 ThreadLocal에 값을 잘 넣었는지 확인
        return "현재 요청자 ID: " + UserContextHolder.getUserId();
    }
}