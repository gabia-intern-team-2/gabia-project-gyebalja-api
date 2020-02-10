package com.gabia.gyebalja.dto.board;

import com.gabia.gyebalja.domain.Board;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Data
public class BoardRequestDto {

    private String title;
    private String content;
    private int views;
    private User user;
    private Education education;

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
