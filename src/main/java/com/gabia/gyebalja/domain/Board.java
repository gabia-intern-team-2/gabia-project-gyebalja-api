package com.gabia.gyebalja.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int views;
  
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

    /**
     * [방법1]
     * 방법1의 경우 Board 객체에서는 특별하게 추가되는 필드는 없습니다!
     * */

    /**
     * [방법2]
     * 설명은 Comment 객체 쪽에 작성하였습니다!
     *
     * */
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="board_id")
    private List<Comment> comments = new ArrayList<>();
    /** 방법2 끝*/


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
