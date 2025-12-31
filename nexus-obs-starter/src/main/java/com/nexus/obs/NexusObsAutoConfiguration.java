package com.nexus.obs;

import io.micrometer.core.instrument.MeterRegistry;
import com.nexus.obs.aop.NexusPerformanceAspect;
import com.nexus.obs.tracing.NexusTraceIdFilter; // import 추가
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean; // import 추가
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(NexusObsProperties.class)
@ConditionalOnProperty(prefix = "nexus.obs", name = "enabled", havingValue = "true", matchIfMissing = true)
@ComponentScan(basePackages = "com.nexus.obs")
public class NexusObsAutoConfiguration {

    public NexusObsAutoConfiguration() {
        log.info("🚀 Nexus Observability Module Initialized...");
    }

    // 1. Trace ID 필터 (기존 코드)
    @Bean
    @ConditionalOnProperty(prefix = "nexus.obs", name = "tracing-enabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<NexusTraceIdFilter> nexusTraceIdFilter() {
        FilterRegistrationBean<NexusTraceIdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new NexusTraceIdFilter());
        registrationBean.addUrlPatterns("/*"); // 모든 요청에 적용
        registrationBean.setOrder(Integer.MIN_VALUE); // 최우선 순위 보장
        return registrationBean;
    }
    
    // 2. 성능 측정 AOP 등록
    @Bean
    @ConditionalOnProperty(prefix = "nexus.obs", name = "metrics-enabled", havingValue = "true", matchIfMissing = true)
    public NexusPerformanceAspect nexusPerformanceAspect(MeterRegistry registry) {
        // MeterRegistry는 스프링 부트가 자동으로 주입해줍니다.
        return new NexusPerformanceAspect(registry);
    }
}