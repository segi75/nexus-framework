package com.nexus.migration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "nexus.migration")
public class NexusMigrationProperties {
    
    // 마이그레이션 기능 활성화 여부 (기본값: true)
    private boolean enabled = true;
}