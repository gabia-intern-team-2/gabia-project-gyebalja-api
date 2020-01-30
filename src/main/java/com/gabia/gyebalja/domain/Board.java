package com.gabia.gyebalja.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Board{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long views;
  
    @OneToMany(mappedBy = "board")
    private List<Likes> likes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_id")
    private Education education;

    @OneToMany(mappedBy = "board")
    private List<BoardImg> boardImgs = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    public void changeEducation(Education education){ this.education = education; }
}
