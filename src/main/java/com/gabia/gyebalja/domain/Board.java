package com.gabia.gyebalja.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString(of = {"id", "title", "content", "views"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_id")
    private Education education;

    @Builder
    public Board(String title, String content, int views, User user, Education education){
        this.title = title;
        this.content = content;
        this.views = views;
        this.user = user;
        this.education = education;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

    public void changeEducation(Education education){ this.education = education; }
}
