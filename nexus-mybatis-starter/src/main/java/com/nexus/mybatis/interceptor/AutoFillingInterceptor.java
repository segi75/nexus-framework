package com.nexus.mybatis.interceptor;

import java.time.LocalDateTime;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import com.nexus.mybatis.audit.AuditAware; // [중요] 인터페이스 import
import com.nexus.mybatis.dto.BaseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component // [중요] 스프링 빈 등록
@RequiredArgsConstructor
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class AutoFillingInterceptor implements Interceptor {

    // [중요] 구체 클래스가 아닌 인터페이스 주입
    private final AuditAware auditorAware;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object param = args[1];

        if (param instanceof BaseDto) {
            BaseDto dto = (BaseDto) param;
            SqlCommandType sqlCommandType = ms.getSqlCommandType();
            
            // [중요] 인터페이스 메서드 호출 (String 반환됨)
            String currentUserId = auditorAware.getCurrentAuditor();
            LocalDateTime now = LocalDateTime.now();

            if (sqlCommandType == SqlCommandType.INSERT) {
                dto.setRegId(currentUserId);
                dto.setRegDt(now);
                dto.setModId(currentUserId);
                dto.setModDt(now);
            } else if (sqlCommandType == SqlCommandType.UPDATE) {
                dto.setModId(currentUserId);
                dto.setModDt(now);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}