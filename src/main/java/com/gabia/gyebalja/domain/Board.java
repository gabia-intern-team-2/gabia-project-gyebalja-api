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
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "edu_id")
    private Long eduId;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    public Board(){
        this.title = "";
        this.content = "";
        this.userId = 0L;
        this.eduId = 0L;
        this.createdDate = LocalDate.now();
        this.modifiedDate = LocalDate.now();
    }

    @Builder
    public Board(String title, String content){
        this.title = title;
        this.content = content;
        this.views = 0L;
        this.likes = 0L;
        this.userId = 0L;
        this.eduId = 0L;
        this.createdDate = LocalDate.now();
        this.modifiedDate = LocalDate.now();
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
