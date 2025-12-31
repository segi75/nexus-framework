package com.nexus.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nexus.sample.dto.TestDto;
import com.nexus.sample.mapper.TestMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestMapper testMapper; // MapStruct가 만든 구현체가 주입됨

    @GetMapping("/api/test")
    public TestDto test(@RequestParam String name) {
        // Mapper 동작 테스트
        return testMapper.toDto(name, 35);
    }
}