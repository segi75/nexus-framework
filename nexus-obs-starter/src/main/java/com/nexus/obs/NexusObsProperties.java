package com.nexus.obs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "nexus.obs")
public class NexusObsProperties {

    /**
     * 관찰성 모듈 전체 활성화 여부
     * default: true
     */
    private boolean enabled = true;

    /**
     * Trace ID (요청 추적) 기능 활성화 여부
     */
    private boolean tracingEnabled = true;

    /**
     * Metrics (성능 측정) 기능 활성화 여부
     */
    private boolean metricsEnabled = true;
}