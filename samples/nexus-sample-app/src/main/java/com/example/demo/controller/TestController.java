package com.example.demo.controller;

import com.nexusframework.core.api.NexusResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    // 1. 성공 케이스 테스트
    @GetMapping("/success")
    public NexusResponse<String> success() {
        // 개발자는 성공 데이터만 넣으면 됩니다. (TraceId, 시간 등은 자동)
        return NexusResponse.success("NEXUS Framework is Running!");
    }

    // 2. 강제 에러 테스트 (GlobalExceptionHandler 동작 확인용)
    @GetMapping("/error")
    public NexusResponse<String> error() {
        // 일부러 에러를 터뜨려봅니다.
        throw new RuntimeException("Test Exception");
    }
}