package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 우리가 만든 프레임워크 패키지(com.nexusframework)도 스캔해야 설정이 먹힙니다.
@SpringBootApplication(scanBasePackages = {"com.example.demo", "com.nexusframework"})
public class NexusSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusSampleApplication.class, args);
    }
}