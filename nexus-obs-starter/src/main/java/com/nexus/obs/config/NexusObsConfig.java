package com.nexus.obs.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * [NEXUS Standard]
 * obs 모듈이 설치되면 자동으로 동작하는 설정 파일입니다.
 * com.nexusframework.obs 패키지 아래에 있는 모든 빈(Filter 등)을 스캔해서 등록합니다.
 */
@Configuration
@ComponentScan(basePackages = "com.nexusframework.obs")
public class NexusObsConfig {
    // 향후 여기에 Actuator 커스텀 설정이나, Logback 설정을 추가할 수 있습니다.
}