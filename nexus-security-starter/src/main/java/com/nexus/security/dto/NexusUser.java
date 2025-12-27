package com.nexus.security.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * [NEXUS Standard] 인증된 사용자 정보 모델
 * - 모든 모듈에서 로그인 사용자 정보를 접근할 때 이 클래스를 사용합니다.
 * - 불변성(Immutable)을 지향하여 사이드 이펙트를 방지합니다.
 */
public class NexusUser implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private final String id;       // 사용자 ID (Unique Key)
    private final String name;     // 사용자 이름 (Display Name)
    private final String role;     // 권한 (예: ROLE_USER, ROLE_ADMIN)
    private final String deptCode; // 부서 코드 (확장성 고려)

    // 생성자 (Constructor)
    public NexusUser(String id, String name, String role, String deptCode) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.deptCode = deptCode;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getDeptCode() { return deptCode; }

    // Spring Security 호환용 Helper
    public List<String> getAuthorities() {
        return role != null ? Collections.singletonList(role) : Collections.emptyList();
    }
    
    @Override
    public String toString() {
        return "NexusUser{id='" + id + "', name='" + name + "', role='" + role + "'}";
    }
}