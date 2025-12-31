package com.nexus.core.context;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class UserContext {
    private String userId;
    private String userName;
    private String role;
    
    // 필요 시 부서 코드, IP 주소 등 확장 가능
}