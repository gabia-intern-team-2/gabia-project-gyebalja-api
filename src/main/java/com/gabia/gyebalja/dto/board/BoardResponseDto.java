package com.gabia.gyebalja.dto.board;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.CommentDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@ToString
@Getter
@Data
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private int views;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private User user;
    private Education education;
    private List<CommentDto> commentList;

    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.views = board.getViews();
        this.createdDate = board.getCreatedDate();
        this.modifiedDate = board.getModifiedDate();
        this.user = board.getUser();
        this.education = board.getEducation();
        this.commentList = new ArrayList<CommentDto>();
    }

    public void changeCommentList(List<CommentDto> commentDtoList){
        this.commentList = commentDtoList;
    }
}
