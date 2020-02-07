package com.gabia.gyebalja.dto.likes;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

@ToString
@Getter
public class LikesRequestDto {

    private Long userId;
    private Long boardId;

    @Builder
    public LikesRequestDto(Long userId, Long boardId){
        Assert.notNull(userId, "UserId must not be null");
        Assert.notNull(boardId, "BoardId must not be null");
        this.userId = userId;
        this.boardId = boardId;
    }
}
