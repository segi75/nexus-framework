package com.nexus.mybatis.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import com.nexus.mybatis.dialect.NexusDialect;
import com.nexus.mybatis.plugin.NexusPagingInterceptor;
import com.nexus.mybatis.plugin.NexusSqlSafetyInterceptor;

class NexusMybatisConfigTest {

    // 스프링 컨텍스트를 시뮬레이션 해주는 도구
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(NexusMybatisConfig.class));

    @Test
    @DisplayName("NEXUS 표준 설정과 안전장치가 정상적으로 등록되어야 한다")
    void configurationLoaded() {
        contextRunner.run(context -> {
            // 1. 설정 클래스가 로드되었는지 확인
            assertThat(context).hasSingleBean(NexusMybatisConfig.class);

            // 2. 안전 장치(Interceptor)가 등록되었는지 확인
            assertThat(context).hasSingleBean(NexusSqlSafetyInterceptor.class);

            // 3. 페이징 감독관(Interceptor)이 등록되었는지 확인
            assertThat(context).hasSingleBean(NexusPagingInterceptor.class);

            // 4. MSSQL 통역사(Dialect)가 등록되었는지 확인
            assertThat(context).hasSingleBean(NexusDialect.class);
            
            System.out.println("✅ NEXUS MyBatis 엔진 정밀 검사 통과!");
        });
    }
}