package com.nexus.web.config;

import com.nexus.web.filter.UserContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean<UserContextFilter> userContextFilter() {
        FilterRegistrationBean<UserContextFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserContextFilter());
        registrationBean.addUrlPatterns("/*"); // 모든 요청에 대해 적용
        registrationBean.setOrder(1); // 가장 먼저 실행되도록 우선순위 높게 설정
        return registrationBean;
    }
}