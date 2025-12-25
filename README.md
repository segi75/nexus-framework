# 🏛️ NEXUS Framework

> **Enterprise Standard Development & Operation Platform**
>
> 사내 표준을 강제하고, 개발 생산성을 자동화하며, 운영 신뢰성을 내장하는 차세대 프레임워크입니다.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green?style=flat-square)
![NEXUS](https://img.shields.io/badge/NEXUS-v1.0.0-blue?style=flat-square)

---

## 📖 개요 (Overview)

**NEXUS Framework**는 단순한 라이브러리 모음이 아닙니다.  
개발자가 비즈니스 로직에만 집중할 수 있도록 **반복적인 설정, 보안, 로깅, 트랜잭션 처리**를 프레임워크 레벨에서 표준화하여 제공합니다.

### 🎯 핵심 가치 (Core Values)
1.  **Standard Enforcement (표준 강제):** API 규격, 에러 처리, 코딩 컨벤션을 빌드 타임에 강제합니다.
2.  **Productivity Automation (생산성):** 페이징, 공통 CRUD, 테스트 환경을 자동 구성합니다.
3.  **Built-in Reliability (신뢰성):** 분산 추적(Tracing), 안전한 쿼리(Safety Plugin), 트랜잭션 정책을 내장합니다.

---

## 📦 모듈 구성 (Modules)

NEXUS v1.0은 7개의 핵심 모듈로 구성되어 있습니다.

| 모듈명 | 설명 | 비고 |
| :--- | :--- | :--- |
| **`nexus-bom`** | **[Bill of Materials]** 모든 라이브러리 버전 통제 (Spring Boot 3.2.2 기반) | **필수** |
| **`nexus-core`** | 공통 모델(Response/Error), 유틸리티, 컨텍스트 정의 | **필수** |
| **`nexus-web-starter`** | 웹 표준(GlobalExceptionHandler, API 규격, 필터) 자동 설정 | Starter |
| **`nexus-obs-starter`** | **[관측성]** TraceId 발급/전파, 로깅(MDC), 마스킹, 메트릭 수집 | Starter |
| **`nexus-mybatis-starter`** | **[DB]** MSSQL 표준, 자동 페이징, **Safety Plugin**(Full Delete 방지) | Starter |
| **`nexus-tx`** | **[트랜잭션]** `@TxRead`, `@TxWrite` 어노테이션 및 롤백 정책 표준화 | Lib |
| **`nexus-test-starter`** | **[테스트]** JUnit 5, Mockito, H2, AssertJ 표준 테스트 환경 통합 | Test |

---

## 🚀 시작하기 (Getting Started)

프로젝트의 `build.gradle`에 아래와 같이 의존성을 추가하면 즉시 표준 환경이 구축됩니다.

### 1. BOM 적용 (버전 관리)
```groovy
dependencies {
    // NEXUS BOM을 통해 모든 모듈의 버전을 관리합니다.
    implementation platform('com.nexus:nexus-bom:1.0.0')
}
2. 필요한 스타터 추가
dependencies {
    // 웹 + 관측성 + DB + 트랜잭션 기능을 한 번에 적용
    implementation 'com.nexus:nexus-web-starter'
    implementation 'com.nexus:nexus-obs-starter'
    implementation 'com.nexus:nexus-mybatis-starter'
    implementation 'com.nexus:nexus-tx'
    
    // 테스트 도구 모음
    testImplementation 'com.nexus:nexus-test-starter'
}
2. 안전한 데이터 액세스 (nexus-mybatis)
Auto Paging: PageRequest 객체만 넘기면 DB 방언(Dialect)에 맞춰 페이징 쿼리가 자동 생성됩니다.

Safety Guard: WHERE 절 없는 UPDATE/DELETE 실행 시 예외를 발생시켜 데이터를 보호합니다.

3. 명시적 트랜잭션 관리 (nexus-tx)
복잡한 옵션 대신 표준 어노테이션을 사용합니다.

@TxRead: 읽기 전용, 성능 최적화 (Timeout 30s)

@TxWrite: 쓰기 전용, 모든 예외(Exception) 발생 시 자동 롤백 (Timeout 60s)

4. 분산 추적 시스템 (nexus-obs)
요청이 들어오는 순간 고유한 TraceId가 발급되며, 로그(MDC)와 응답 헤더, 에러 메시지 등 모든 구간에 자동 전파됩니다.

🛠️ 기술 스택 (Tech Stack)
Language: Java 21 LTS

Framework: Spring Boot 3.2.2

Build Tool: Gradle 8.x (Kotlin/Groovy DSL)

ORM: MyBatis 3.x

Database: MSSQL (Primary), H2 (Test)

📅 로드맵 (Roadmap)
v1.0 (Current): Core, Web, Obs, MyBatis, Tx, Test 모듈 구축 완료

v2.0 (Planned): Security(인증/인가), Audit(감사로그), Idempotency(멱등성)

v3.0 (Planned): Outbox Pattern, Reliability(재처리), Code Generator

👨‍💻 Maintainers
Architect: NEXUS Architecture Team

Repository: https://github.com/segi75/nexus-framework

Copyright © 2025 NEXUS Framework. All Rights Reserved.
