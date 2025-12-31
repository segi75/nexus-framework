package com.nexus.core.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * [공통 DTO] 모든 테이블의 공통 컬럼(Audit) 정의
 * 개발자는 이 클래스를 상속받기만 하면 됩니다.
 */
@Getter
@Setter
public class NexusBaseDTO {
    private String regId;        // 등록자 ID
    private LocalDateTime regDt; // 등록 일시
    private String modId;        // 수정자 ID
    private LocalDateTime modDt; // 수정 일시
}