package com.community.domain.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReqCommentDto {
    String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
