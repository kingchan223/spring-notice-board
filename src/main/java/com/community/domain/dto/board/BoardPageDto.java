package com.community.domain.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class BoardPageDto {

    Integer[] pageList;
    List<BoardDto> boardList;
}
