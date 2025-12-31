package com.nexus.mybatis.plugin;

import com.nexus.mybatis.dialect.NexusDialect;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

@Intercepts({
    @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class NexusPagingInterceptor implements Interceptor {

    private final NexusDialect dialect;

    public NexusPagingInterceptor(NexusDialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);

        // 1. 현재 실행할 SQL과 파라미터 가져오기
        // (MyBatis 프록시 구조 때문에 루프를 돌며 원본 객체를 찾습니다)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = SystemMetaObject.forObject(object);
        }

        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        Object parameterObject = boundSql.getParameterObject();

        // 2. 파라미터가 'PageRequest' 규격인지 검사 (Duck Typing)
        // (nexus-core의 PageRequest 클래스를 직접 의존하지 않고, 필드 존재 여부로 판단하여 유연성 확보)
        if (parameterObject != null && hasPagingFields(parameterObject)) {
            int page = (int) getFieldValue(parameterObject, "page");
            int size = (int) getFieldValue(parameterObject, "size");
            long offset = (long) (page - 1) * size;

            // 3. 통역사를 통해 SQL 변환
            String originalSql = boundSql.getSql();
            String pagingSql = dialect.convertToPagingSql(originalSql, offset, size);

            // 4. 변환된 SQL로 바꿔치기 (Reflection)
            metaStatementHandler.setValue("delegate.boundSql.sql", pagingSql);
        }

        return invocation.proceed();
    }

    // "page"와 "size" 필드가 있는지 검사하는 헬퍼 메서드
    private boolean hasPagingFields(Object param) {
        try {
            if (param instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) param;
                return map.containsKey("page") && map.containsKey("size");
            }
            // 객체인 경우 Getter나 필드 확인
            param.getClass().getMethod("getPage");
            param.getClass().getMethod("getSize");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 필드 값을 꺼내는 헬퍼 메서드
    private Object getFieldValue(Object param, String name) {
        try {
            if (param instanceof Map) {
                return ((Map<?, ?>) param).get(name);
            }
            String methodName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
            return param.getClass().getMethod(methodName).invoke(param);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}
}