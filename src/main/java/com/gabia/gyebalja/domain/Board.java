package com.gabia.gyebalja.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@ToString
@Getter
@Entity
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long views;
    private Long likes;
    private LocalDate created_date;
    private LocalDate modified_date;
    private Long user_id;
    private Long edu_id;

    public Board(){
        this.title = "";
        this.content = "";
        this.created_date = LocalDate.now();
        this.modified_date = LocalDate.now();
        this.user_id = 0L;
        this.edu_id = 0L;
    }

    @Builder
    public Board(String title, String content){
        this.title = title;
        this.content = content;
        this.views = 0L;
        this.likes = 0L;
        this.created_date = LocalDate.now();
        this.modified_date = LocalDate.now();
        this.user_id = 0L;
        this.edu_id = 0L;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
