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
