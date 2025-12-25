package com.nexus.mybatis.dialect;

public interface NexusDialect {

    /**
     * 원본 SQL을 받아서, 페이징 처리가 된 SQL로 변환합니다.
     * @param originalSql 원본 쿼리
     * @param offset 건너뛸 개수 (page * size)
     * @param limit 가져올 개수 (size)
     * @return 페이징 적용된 SQL
     */
    String convertToPagingSql(String originalSql, long offset, int limit);
}