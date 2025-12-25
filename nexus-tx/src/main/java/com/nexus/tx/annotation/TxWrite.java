package com.nexus.tx.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Transactional;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional(rollbackFor = Exception.class) // 핵심: 어떤 에러가 나도 무조건 롤백
public @interface TxWrite {

    @AliasFor(annotation = Transactional.class)
    int timeout() default 60; // 기본 60초 (쓰기는 신중하게)

    @AliasFor(annotation = Transactional.class)
    boolean readOnly() default false;
}