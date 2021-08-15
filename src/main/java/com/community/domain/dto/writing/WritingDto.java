package com.community.domain.dto.writing;

import com.community.domain.entity.Writing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WritingDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private String date;

    public static WritingDto createWritingDto(Writing writing){
        WritingDto writingDto = new WritingDto();
        writingDto.setId(writing.getId());
        writingDto.setTitle(writing.getTitle());
        writingDto.setAuthor(writing.getMember().getName());
        writingDto.setContent(writing.getContent());
        writingDto.setDate(writing.getDate().toString());
        return writingDto;
    }
}
