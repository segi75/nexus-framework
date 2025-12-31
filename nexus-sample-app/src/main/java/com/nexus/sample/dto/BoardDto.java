package com.nexus.sample.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BoardDto {
    private Long boardId;
    private String title;
    private String writer;
    private Integer viewCount;
    private LocalDateTime createdAt;
}