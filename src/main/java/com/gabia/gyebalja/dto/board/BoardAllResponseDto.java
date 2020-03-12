package com.gabia.gyebalja.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabia.gyebalja.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Author : 이현재
 * Part : All
 */

@NoArgsConstructor
@ToString
@Getter
public class BoardAllResponseDto {

    private Long id;
    private String title;
    private int views;
    private int likes;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedDate;
    private Long userId;
    private String userName;
    private Long educationId;
    private String educationTitle;

    public BoardAllResponseDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.views = board.getViews();
        this.likes = 0;
        this.createdDate = board.getCreatedDate();
        this.modifiedDate = board.getModifiedDate();
        this.userId = board.getUser().getId();
        this.userName = board.getUser().getName();
        this.educationId = board.getEducation().getId();
        this.educationTitle = board.getEducation().getTitle();
    }

    public void changeLikes(int likes){
        this.likes = likes;
    }
}
