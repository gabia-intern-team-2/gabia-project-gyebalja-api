package com.gabia.gyebalja.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@ToString
@Getter
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Column(name = "board_id")
    private Long boardId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column (name = "modified_date")
    private LocalDate modifiedDate;

    public Comment(){
        this.boardId = 1L;
        this.userId = 1L;
        this.createdDate = LocalDate.now();
        this.modifiedDate = LocalDate.now();
    }

    @Builder
    public Comment(String content){
        this.content = content;
        this.boardId = 1L;
        this.userId = 1L;
        this.createdDate = LocalDate.now();
        this.modifiedDate = LocalDate.now();
    }

    public void changeContent(String content){
        this.content = content;
    }
}
