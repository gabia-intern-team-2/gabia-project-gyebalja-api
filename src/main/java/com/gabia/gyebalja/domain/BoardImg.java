package com.gabia.gyebalja.domain;

import lombok.*;

import javax.persistence.*;

@ToString(of = {"id", "imgPath"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "board_img")
public class BoardImg extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "img_path")
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
