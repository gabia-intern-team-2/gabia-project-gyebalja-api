package com.gabia.gyebalja.domain;

import com.gabia.gyebalja.dto.board.BoardRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@ToString(of = {"id", "title", "content", "views"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends BaseTime {

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

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Board(String title, String content, int views, User user, Education education){
        this.title = title;
        this.content = content;
        this.views = views;
        this.user = user;
        this.education = education;
    }

    public void changeTitle(String title) { this.title = title; }

    public void changeContent(String content) { this.content = content; }

    public void changeEducation(Education education) { this.education = education; }

    public void changeBoard(BoardRequestDto boardRequestDto, Education education){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.education = education;
    }

    public void upViews() { this.views = this.views + 1; }
}
