package com.gabia.gyebalja.dto;

import com.gabia.gyebalja.domain.Board;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ToString
@Getter
@Data
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private Long view;
    private Long like;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private Long userId;
    private Long eduId;
    private List<CommentDto> commentList;

    @Builder
    public BoardDto(String title, String content){
        this.title = title;
        this.content = content;
        this.view = 0L;
        this.like = 0L;
        this.createdDate = LocalDate.now();
        this.modifiedDate = LocalDate.now();
        this.userId = 0L;
        this.eduId = 0L;
        this.commentList = new ArrayList<CommentDto>();
    }

    public BoardDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.view = board.getViews();
        this.like = board.getLikes();
        this.createdDate = LocalDate.now();
        this.modifiedDate = LocalDate.now();
        this.userId = board.getUserId();
        this.eduId = board.getEduId();
        this.commentList = new ArrayList<CommentDto>();
    }

    public void changeCommentList(List<CommentDto> commentDtoList){
        this.commentList = commentDtoList;
    }

    public Board toEntity(){
        return Board.builder().title(title).content(content).build();
    }
}
