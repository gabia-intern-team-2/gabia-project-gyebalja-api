package com.gabia.gyebalja.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BoardImg {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String imgPath;

    @Builder
    public BoardImg(Board board, String imgPath){
        this.board = board;
        this.imgPath = imgPath;
    }

    public void changeImgPath(String imgPath){
        this.imgPath = imgPath;
    }
}
