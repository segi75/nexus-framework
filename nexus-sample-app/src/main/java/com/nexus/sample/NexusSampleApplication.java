package com.nexus.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.nexus", "com.nexus.sample"}) // 프레임워크와 샘플 앱 모두 스캔
public class NexusSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusSampleApplication.class, args);
    }
}