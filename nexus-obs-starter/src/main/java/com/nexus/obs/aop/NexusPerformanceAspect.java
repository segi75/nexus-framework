package com.nexus.obs.aop;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class NexusPerformanceAspect {

    private final MeterRegistry meterRegistry;

    // 타겟 정의: com.nexus 패키지 하위의 Service 어노테이션이 붙은 클래스의 모든 메소드
    @Around("execution(* com.nexus..*Service.*(..))") 
    public Object measureExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        
        // 1. 시작 시간 기록
        Timer.Sample sample = Timer.start(meterRegistry);
        
        // 에러 발생 여부 태그용
        String status = "success";
        
        try {
            // 2. 실제 비즈니스 로직 실행
            return pjp.proceed();
        } catch (Throwable ex) {
            status = "error";
            throw ex; // 예외는 다시 밖으로 던져줘야 함
        } finally {
            // 3. 종료 시간 기록 및 메트릭 등록
            String className = pjp.getSignature().getDeclaringTypeName();
            String methodName = pjp.getSignature().getName();

            // 'nexus.method.execution' 이라는 이름으로 메트릭 생성
            sample.stop(Timer.builder("nexus.method.execution")
                    .description("Execution time of Service methods")
                    .tag("class", className)
                    .tag("method", methodName)
                    .tag("status", status)
                    .register(meterRegistry));
        }
    }
}