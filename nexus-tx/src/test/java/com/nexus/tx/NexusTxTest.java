package com.nexus.tx;

import com.nexus.tx.annotation.TxRead;
import com.nexus.tx.annotation.TxWrite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

class NexusTxTest {

    @Test
    @DisplayName("@TxRead는 readOnly=true 옵션을 반드시 포함해야 한다")
    void txReadCheck() {
        // @TxRead가 붙은 가짜 클래스
        @TxRead
        class ReadSample {}

        // 스프링의 유틸로 속성 추출
        Transactional tx = AnnotatedElementUtils.findMergedAnnotation(ReadSample.class, Transactional.class);

        assertThat(tx).isNotNull();
        assertThat(tx.readOnly()).isTrue(); // 읽기 전용인지 확인
    }

    @Test
    @DisplayName("@TxWrite는 모든 예외(Exception)에 대해 롤백해야 한다")
    void txWriteCheck() {
        // @TxWrite가 붙은 가짜 클래스
        @TxWrite
        class WriteSample {}

        Transactional tx = AnnotatedElementUtils.findMergedAnnotation(WriteSample.class, Transactional.class);

        assertThat(tx).isNotNull();
        assertThat(tx.readOnly()).isFalse(); // 쓰기 가능인지 확인
        assertThat(tx.rollbackFor()).contains(Exception.class); // 롤백 정책 확인
    }
}