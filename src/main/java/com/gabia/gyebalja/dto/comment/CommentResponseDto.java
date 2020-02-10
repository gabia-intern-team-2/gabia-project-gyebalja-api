package com.gabia.gyebalja.dto.comment;

import com.gabia.gyebalja.domain.Comment;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Getter
@Data
public class CommentResponseDto {

    private Long id;
    private String content;
    private Long boardId;
    private Long userId;
    private String userName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.boardId = comment.getBoard().getId();
        this.userId = comment.getUser().getId();
        this.userName = comment.getUser().getName();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }

}
