package com.gabia.gyebalja.dto;

import com.gabia.gyebalja.domain.Comment;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@ToString
@Getter
@Data
public class CommentDto {
//    private Long id;
//    private String content;
//    private Long boardId;
//    private Long userId;
//    private LocalDate createdDate;
//    private LocalDate modifiedDate;
//
//    @Builder
//    public CommentDto(String content){
//        this.content = content;
//        this.boardId = 1L;
//        this.userId = 1L;
//        this.createdDate = LocalDate.now();
//        this.modifiedDate = LocalDate.now();
//    }
//
//    public CommentDto(Comment comment){
//        this.id = comment.getId();
//        this.content = comment.getContent();
//        this.boardId = comment.getBoardId();
//        this.userId = comment.getUserId();
//        this.createdDate = comment.getCreatedDate();
//        this.modifiedDate = comment.getModifiedDate();
//    }
//
//    public Comment toEntity(){
//        return Comment.builder().content(content).build();
//    }
}
