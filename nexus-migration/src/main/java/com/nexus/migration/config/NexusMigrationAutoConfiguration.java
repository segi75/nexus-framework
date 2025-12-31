package com.nexus.migration.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails; // [New]
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;

import com.nexus.migration.properties.NexusMigrationProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfiguration(before = FlywayAutoConfiguration.class)
@ConditionalOnClass(Flyway.class)
@EnableConfigurationProperties(NexusMigrationProperties.class)
@ConditionalOnProperty(prefix = "nexus.migration", name = "enabled", havingValue = "true", matchIfMissing = true)
public class NexusMigrationAutoConfiguration {

    @Bean
    public FlywayConfigurationCustomizer nexusFlywayCustomizer(JdbcConnectionDetails connectionDetails) { // [Change] DataSource 대신 주입
        return configuration -> {
            // 1. ConnectionDetails에서 URL 추출 및 드라이버 감지 (Safe Way)
            String url = connectionDetails.getJdbcUrl();
            DatabaseDriver driver = DatabaseDriver.fromJdbcUrl(url);
            String vendor = driver.getId();

            // 2. 벤더별 폴더 경로 매핑
            // sqlserver인 경우 폴더명을 "mssql"로 통일
            String folderName = "sqlserver".equals(vendor) ? "mssql" : vendor;
            String migrationPath = "classpath:db/migration/" + folderName;

            log.info("[NEXUS] DB Migration detected database: [{}] -> path: [{}]", vendor, migrationPath);

            // 3. Flyway 설정 강제 적용
            configuration
                .locations(migrationPath)
                .baselineOnMigrate(true)
                .validateOnMigrate(true);
        };
    }
}