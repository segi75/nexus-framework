package com.nexus.sample.controller;

import com.nexus.sample.dto.BoardDto;
import com.nexus.sample.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController                  // <--- 이거 필수!
@RequestMapping("/boards")       // <--- 이게 주소("/boards") 입니다.
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public List<BoardDto> getBoards() {
        return boardService.getBoardList();
    }
}