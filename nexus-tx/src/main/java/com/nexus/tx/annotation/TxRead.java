package com.nexus.tx.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Transactional;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional(readOnly = true) // 핵심: 무조건 읽기 전용으로 동작
public @interface TxRead {

    // @Transactional의 옵션을 덮어쓸 수 있도록 열어둡니다.
    @AliasFor(annotation = Transactional.class)
    int timeout() default 30; // 기본 30초 (조회는 길어질 수 있으므로)

    @AliasFor(annotation = Transactional.class)
    String value() default "";
}