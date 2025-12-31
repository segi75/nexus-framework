package com.nexus.sample.mapper;

import com.nexus.sample.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> selectBoardList();
}