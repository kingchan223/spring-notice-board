package com.community.domain.dto.board;

import com.community.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {
    String content;
    String authorLoginId;
    String date;

    public static List<CommentDto> createCommentDtos(List<Comment> comments){
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(createCommentDto(comment));
        }
        return commentDtos;
    }

    private static CommentDto createCommentDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(comment.getContent());
        commentDto.setAuthorLoginId(comment.getMember().getLoginId());
        commentDto.setDate(comment.getDate().toString());
        return commentDto;
    }
}
