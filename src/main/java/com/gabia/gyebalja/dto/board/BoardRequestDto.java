package com.gabia.gyebalja.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class BoardRequestDto {

    private String title;
    private String content;
    private int views;
    private Long userId;
    private Long educationId;

    @Builder
    public BoardRequestDto(String title, String content, Long userId, Long educationId){
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.educationId = educationId;
    }
}
