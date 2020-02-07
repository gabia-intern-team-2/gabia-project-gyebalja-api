package com.gabia.gyebalja.dto.likes;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LikesRequestDto {

    private Long userId;
    private Long boardId;

    @Builder
    public LikesRequestDto(Long userId, Long boardId){
        this.userId = userId;
        this.boardId = boardId;
    }
}
