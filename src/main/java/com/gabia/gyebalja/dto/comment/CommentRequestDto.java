package com.gabia.gyebalja.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class CommentRequestDto {

    private String content;
    private Long boardId;
    private Long userId;

    @Builder
    public CommentRequestDto(String content, Long boardId, Long userId) {
        this.content = content;
        this.boardId = boardId;
        this.userId = userId;
    }
}
