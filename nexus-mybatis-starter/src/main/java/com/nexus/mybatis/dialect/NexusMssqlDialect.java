package com.nexus.mybatis.dialect;

public class NexusMssqlDialect implements NexusDialect {

    @Override
    public String convertToPagingSql(String originalSql, long offset, int limit) {
        StringBuilder sql = new StringBuilder(originalSql.trim());
        
        // MSSQL은 OFFSET 사용 시 ORDER BY가 필수입니다.
        // 쿼리에 ORDER BY가 없으면, 에러 방지를 위해 임시 정렬(SELECT NULL)을 추가합니다.
        if (!originalSql.toUpperCase().contains("ORDER BY")) {
            sql.append(" ORDER BY (SELECT NULL)");
        }

        // MSSQL 2012+ 표준 페이징 문법 적용
        sql.append(" OFFSET ").append(offset).append(" ROWS");
        sql.append(" FETCH NEXT ").append(limit).append(" ROWS ONLY");

        return sql.toString();
    }
}