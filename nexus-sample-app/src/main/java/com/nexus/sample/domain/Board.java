package com.nexus.sample.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Board {
    private Long boardId;      // PK
    private String title;
    private String content;
    private String writer;
    private Integer viewCount;
    private LocalDateTime createdAt;
}