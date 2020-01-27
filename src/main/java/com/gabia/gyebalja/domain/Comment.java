package com.gabia.gyebalja.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@ToString
@Getter
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Long board_id;
    private Long user_id;
    private LocalDate created_date;
    private LocalDate modified_date;

    public Comment(){
        this.board_id = 1L;
        this.user_id = 1L;
        this.created_date = LocalDate.now();
        this.modified_date = LocalDate.now();
    }

    @Builder
    public Comment(String content){
        this.content = content;
        this.board_id = 1L;
        this.user_id = 1L;
        this.created_date = LocalDate.now();
        this.modified_date = LocalDate.now();
    }

    public void changeContent(String content){
        this.content = content;
    }
}
