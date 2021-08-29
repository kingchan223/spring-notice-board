package com.community.domain.dto.board;

import com.community.domain.entity.Board;
import com.community.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private String date;
    private String loginId;
    private List<Comment> comments;

    public static BoardDto createboardDto(Board board){
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setAuthor(board.getMember().getName());
        boardDto.setContent(board.getContent());
        boardDto.setDate(board.getDate().toString());
        boardDto.setLoginId(board.getMember().getLoginId());
        boardDto.setComments(board.getComments());
        return boardDto;
    }
}
