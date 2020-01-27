package com.gabia.gyebalja.dto;

import com.gabia.gyebalja.domain.Board;
import lombok.*;

import java.time.LocalDate;

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
    }

    public BoardDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.view = board.getViews();
        this.like = board.getLikes();
        this.createdDate = LocalDate.now();
        this.modifiedDate = LocalDate.now();
        this.userId = board.getUser_id();
        this.eduId = board.getEdu_id();
    }

    public Board toEntity(){
        return Board.builder().title(title).content(content).build();
    }
}
