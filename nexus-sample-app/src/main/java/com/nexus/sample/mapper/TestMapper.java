package com.nexus.sample.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.nexus.sample.dto.TestDto;

// componentModel = "spring" : 스프링 빈으로 자동 등록 (우리가 build.gradle에 설정함)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestMapper {
    
    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

    // 단순 테스트용: 입력을 받아 메시지만 가공해서 리턴하는 로직
    @Mapping(target = "message", expression = "java(name + \"님, NEXUS 프레임워크에 오신 것을 환영합니다!\")")
    TestDto toDto(String name, int age);
}