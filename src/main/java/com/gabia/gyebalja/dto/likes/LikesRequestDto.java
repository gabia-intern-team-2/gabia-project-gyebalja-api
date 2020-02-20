package com.gabia.gyebalja.dto.likes;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Likes;
import com.gabia.gyebalja.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class LikesRequestDto {

    private Long userId;
    private Long boardId;

    @Builder
    public LikesRequestDto(Long userId, Long boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }

    public Likes toEntity(User user, Board board) {
        return Likes.builder()
                .user(user)
                .board(board)
                .build();
    }
}
