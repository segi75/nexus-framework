package com.nexus.sample.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MetricTestController {

    private final MetricTestService testService;

    @GetMapping("/api/test-metrics")
    public String test() {
        log.info("테스트 API 호출됨!");
        return testService.process();
    }
}

// 간단하게 같은 파일에 Service 생성 (테스트용)
@Slf4j
@Service
class MetricTestService {
    public String process() {
        log.info("Service 로직 실행 중... (시간 측정 대상)");
        try {
            Thread.sleep(100); // 0.1초 대기 (측정값 확인용)
        } catch (InterruptedException e) {}
        return "OK";
    }
}