📝 NEXUS Framework - README.md (v2.0)
Markdown

# 🏛️ NEXUS Framework

> **Enterprise Standard Development & Operation Platform**
>
> NEXUS는 Spring Boot 3 + Java 21 기반의 엔터프라이즈 프레임워크로,
> 사내 표준을 강제하고, 개발 생산성을 자동화하며, "마법 같은 개발자 경험(DX)"을 제공합니다.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green?style=flat-square)
![NEXUS](https://img.shields.io/badge/NEXUS-v2.0.0-blue?style=flat-square)

---

## 🚀 Project Status
현재 **Phase 2.0 (Security)** 단계까지 구축 및 검증이 완료되었습니다.

| Version | Phase | Status | Key Feature |
|:---:|:---:|:---:|:---|
| v1.0 | 1.0 | ✅ Completed | Core, Web, MyBatis, Logging, Tx |
| v1.5 | 1.5 | ✅ Completed | Sample App Verification (End-to-End) |
| **v2.0** | **2.0** | **✅ Completed** | **Security (JWT, Zero Config), @CurrentUser** |
| v2.1 | 2.1 | 🚧 Planned | DB Migration (Flyway) |

---

## 📖 개요 (Overview)

**NEXUS Framework**는 단순한 라이브러리 모음이 아닙니다.  
개발자가 비즈니스 로직에만 집중할 수 있도록 **보안, 로깅, 트랜잭션, 데이터 처리**를 프레임워크 레벨에서 표준화하고 자동화합니다.

### 🎯 핵심 가치 (Core Values)
1.  **Zero Configuration (자동 설정):** 의존성 추가만으로 보안, 로깅, 표준 설정이 즉시 적용됩니다.
2.  **Productivity Automation (생산성):** 페이징, 공통 CRUD, 인증 정보 주입을 자동화합니다.
3.  **Built-in Reliability (신뢰성):** 분산 추적(Tracing), 안전한 쿼리(Safety Guard), 트랜잭션 정책을 내장합니다.

---

## 📦 모듈 구성 (Modules)

NEXUS는 Flat Structure Multi-Module 구조를 따르며, 8개의 핵심 모듈로 구성됩니다.

| 모듈명 | 설명 | 비고 |
| :--- | :--- | :--- |
| **`nexus-bom`** | **[Bill of Materials]** 모든 라이브러리 버전 통제 | **필수** |
| **`nexus-core`** | 공통 모델(Response/Error), 유틸리티, 컨텍스트 정의 | **필수** |
| **`nexus-security-starter`** | **[보안 - New]** **Zero-Config JWT 인증**, `@CurrentUser` 자동 주입 | Starter |
| **`nexus-web-starter`** | 웹 표준(GlobalExceptionHandler, API 규격) 자동 설정 | Starter |
| **`nexus-obs-starter`** | **[관측성]** TraceId 발급/전파, 로깅(MDC), 마스킹 | Starter |
| **`nexus-mybatis-starter`** | **[DB]** MSSQL 표준, 자동 페이징, **Safety Plugin**(Full Delete 방지) | Starter |
| **`nexus-tx`** | **[트랜잭션]** `@TxRead`, `@TxWrite` 어노테이션 표준화 | Lib |
| **`nexus-test-starter`** | **[테스트]** JUnit 5, Mockito, H2 통합 테스트 환경 | Test |

---

## 🛠️ 시작하기 (Getting Started)

### 1. Security 모듈 적용 (v2.0 핵심 기능)
`build.gradle`에 의존성 한 줄만 추가하면 **JWT 인증 및 보안 설정**이 자동으로 적용됩니다.

```groovy
dependencies {
    implementation project(':nexus-security-starter')
}
사용 예시 (Controller): 복잡한 SecurityContextHolder 호출 없이, 어노테이션 하나로 인증 정보를 주입받습니다.

Java

@GetMapping("/me")
public ResponseEntity<NexusUser> getMyInfo(@CurrentUser NexusUser user) {
    // user 객체는 이미 안전하게 주입되었습니다. (Null Safe, Type Safe)
    log.info("Access User: {}", user.getName());
    return ResponseEntity.ok(user);
}
```

2. 안전한 데이터 액세스 (nexus-mybatis)
Auto Paging: PageRequest 객체만 넘기면 DB 방언(Dialect)에 맞춰 쿼리가 자동 생성됩니다.

Safety Guard: WHERE 절 없는 UPDATE/DELETE 실행 시 예외를 발생시켜 대형 사고를 방지합니다.

3. 명시적 트랜잭션 관리 (nexus-tx)
@TxRead: 읽기 전용, 성능 최적화 (Timeout 30s)

@TxWrite: 쓰기 전용, 모든 예외 발생 시 자동 롤백 (Timeout 60s)

🛠️ 기술 스택 (Tech Stack)
Language: Java 21 LTS

Framework: Spring Boot 3.2.2

Build Tool: Gradle 8.x (Kotlin/Groovy DSL)

ORM: MyBatis 3.x

Database: MSSQL (Primary), H2 (Test)

Auth: Spring Security + JJWT 0.12.x

📅 로드맵 (Roadmap)
v1.0 (Completed): Core, Web, Obs, MyBatis, Tx, Test 모듈 구축

v1.5 (Completed): Nexus Sample App 통합 검증

v2.0 (Completed): Security Module (JWT, AutoConfig)

v2.1 (Planned): DB Migration Strategy (Flyway)

v2.2 (Planned): Audit Logging (Data Change Tracking)

v3.0 (Planned): Outbox Pattern, Reliability(재처리), Code Generator

👨‍💻 Maintainers
Architect & Developer: Segi (With AI Partner)

Repository: https://github.com/segi75/nexus-framework

Copyright © 2025 NEXUS Framework. All Rights Reserved.