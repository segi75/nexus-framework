package com.nexus.mybatis.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BaseDto {
    private String regId;       // 등록자 ID
    private LocalDateTime regDt; // 등록일시
    private String modId;       // 수정자 ID
    private LocalDateTime modDt; // 수정일시
}