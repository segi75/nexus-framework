package com.nexus.mybatis.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nexus.mybatis.dialect.NexusDialect;
import com.nexus.mybatis.dialect.NexusMssqlDialect;
import com.nexus.mybatis.plugin.NexusSqlSafetyInterceptor;

@Configuration
public class NexusMyBatisConfig {

    /**
     * MyBatis 표준 설정을 강제 적용합니다. (Spec 6.1)
     * 개발자가 application.yml에 별도로 설정하지 않아도 기본 적용됩니다.
     */
    @Bean
    public ConfigurationCustomizer nexusMyBatisCustomizer(NexusDialect dialect) {
        return configuration -> {
            // 1. CamelCase 자동 변환 (user_id -> userId)
            configuration.setMapUnderscoreToCamelCase(true);

            // 2. 쿼리 타임아웃 30초 강제 (DB 락 방지)
            configuration.setDefaultStatementTimeout(30);

            // 3. 한 번에 가져올 데이터 크기 (메모리 효율)
            configuration.setDefaultFetchSize(100);
            
            // 4. NULL 값 처리 (필요 시 oracle 등 호환성 위해)
            configuration.setJdbcTypeForNull(org.apache.ibatis.type.JdbcType.NULL);
            
            // 안전 장치(Interceptor) 등록
            configuration.addInterceptor(new NexusSqlSafetyInterceptor());
        };
    }
    
    /**
     * [추가됨] MSSQL 방언 통역사를 빈으로 등록합니다.
     * 나중에 MySQL로 바꾸고 싶으면 이 부분만 NexusMysqlDialect로 바꾸면 됩니다.
     */
    @Bean
    public NexusDialect nexusDialect() {
        return new NexusMssqlDialect();
    }
}