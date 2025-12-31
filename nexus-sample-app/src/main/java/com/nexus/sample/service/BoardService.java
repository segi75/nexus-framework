package com.nexus.sample.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexus.sample.dto.BoardDto;
import com.nexus.sample.mapper.BoardMapper;
import com.nexus.sample.struct.BoardStruct;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // NEXUS TX 모듈 검증 (기본 트랜잭션)
public class BoardService {

    private final BoardMapper boardMapper;
    private final BoardStruct boardStruct; // MapStruct가 만든 구현체 주입

    public List<BoardDto> getBoardList() {
        // 1. DB에서 Entity 조회
        var entities = boardMapper.selectBoardList();

        // 2. Entity -> DTO 변환 (MapStruct 활용)
        // Java Stream API와 함께 사용하면 매우 깔끔합니다.
        return entities.stream()
                .map(boardStruct::toDto)
                .toList();
    }
}