package com.nexus.mybatis.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import java.util.Properties;

@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class NexusSqlSafetyInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        
        // 1. INSERT는 검사 제외 (안전함)
        if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
            return invocation.proceed();
        }

        // 2. 실행될 SQL 가져오기
        Object parameter = args[1];
        String sql = ms.getBoundSql(parameter).getSql().trim().toUpperCase();

        // 3. 안전 검사: UPDATE/DELETE인데 WHERE가 없는가?
        // (단순 문자열 체크 방식, 향후 파서 도입 가능)
        boolean isModify = ms.getSqlCommandType() == SqlCommandType.UPDATE || ms.getSqlCommandType() == SqlCommandType.DELETE;
        if (isModify && !sql.contains("WHERE")) {
            throw new SecurityException("[NEXUS-GUARD] 위험한 쿼리가 차단되었습니다. UPDATE/DELETE 시에는 반드시 WHERE 절이 필요합니다. SQL: " + sql);
        }

        // 통과하면 실행
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return org.apache.ibatis.plugin.Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 설정 필요 없음
    }
}