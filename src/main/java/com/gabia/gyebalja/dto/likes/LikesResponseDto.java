package com.gabia.gyebalja.dto.likes;

import com.gabia.gyebalja.domain.Likes;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LikesResponseDto {

    private Long id;
    private Long userId;
    private String userName;
    private Long boardId;
    private String boardTitle;

    public LikesResponseDto(Likes likes){
        this.id = likes.getId();
        this.userId = likes.getUser().getId();
        this.userName = likes.getUser().getName();
        this.boardId = likes.getBoard().getId();
        this.boardTitle = likes.getBoard().getTitle();
    }
}
