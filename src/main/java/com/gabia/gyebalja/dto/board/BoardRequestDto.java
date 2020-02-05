package com.gabia.gyebalja.dto.board;

import com.gabia.gyebalja.domain.*;
import com.gabia.gyebalja.dto.CommentDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@ToString
@Getter
@Data
public class BoardRequestDto {

    private Long id;
    private String title;
    private String content;
    private int views;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private User user;
    private Education education;
    private List<CommentDto> commentList;

    @Builder
    public BoardRequestDto(String title, String content, User user, Education education){
        this.title = title;
        this.content = content;
        this.user = user;
        this.education = education;
    }

    public Board toEntity(){
        return Board.builder().title(title).content(content).views(0).user(user).education(education).build();
    }
}
