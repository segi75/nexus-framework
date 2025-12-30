package com.nexus.mybatis.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nexus.mybatis.dialect.NexusDialect;
import com.nexus.mybatis.dialect.NexusMssqlDialect;

@Configuration
public class NexusMybatisConfig {

    // 1. MyBatis 기본 설정 (CamelCase 등)
    @Bean
    public ConfigurationCustomizer nexusMyBatisCustomizer() {
        return configuration -> {
            configuration.setMapUnderscoreToCamelCase(true);
            configuration.setDefaultStatementTimeout(30);
            configuration.setDefaultFetchSize(100);
            configuration.setJdbcTypeForNull(org.apache.ibatis.type.JdbcType.NULL);
        };
    }
    
    // 2. MSSQL 방언 설정
    @Bean
    public NexusDialect nexusDialect() {
        return new NexusMssqlDialect();
    }

    /* * [삭제됨] 3. defaultAuditorAware
     * 사유: NexusAuditorAware 클래스에 @Component를 붙여 자동 등록되므로 삭제함.
     * ("SYSTEM" 기본값 처리는 NexusAuditorAware 내부 로직으로 이동됨)
     */

    /* * [삭제됨] 4. autoFillingInterceptor
     * 사유: AutoFillingInterceptor 클래스에 @Component를 붙여 자동 등록되므로 삭제함.
     * (MyBatis Starter가 @Component 붙은 Interceptor를 자동으로 감지함)
     */
}