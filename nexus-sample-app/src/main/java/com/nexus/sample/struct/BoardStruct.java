package com.nexus.sample.struct;

import com.nexus.sample.domain.Board;
import com.nexus.sample.dto.BoardDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BoardStruct {
    BoardStruct INSTANCE = Mappers.getMapper(BoardStruct.class);

    BoardDto toDto(Board entity);
    Board toEntity(BoardDto dto);
}