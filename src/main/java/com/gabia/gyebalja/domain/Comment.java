package com.gabia.gyebalja.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;
    // 나머지 동일

    /**
     * [방법1 - ManyToOne]
     * Comment 에서 Board 객체를 갖고 있다. (ManyToOne)
     * 이 경우, Board에서 Comment에 대한 필드를 가질 필요 없다.
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    /** 방법1 끝 */

    /**
     * [방법2 - OneToMany]
     * Board 에서 List<Comment>를 갖고 있다. (OneToMany)
     * 이 경우, Comment에서 Long board_id의 필드는 가져도되고, 안가져도 된다.
     * DB 테이블에는 board_id의 컬럼이 있는데, 이 값은 Board 쪽에서 List.add(comment객체)를 해주면 jpa가 알아서 insert, update 해준다.
     * insert의 과정에서 board_id에 null 값이 들어가고 update의 과정에서 board 객체의 id를 세팅해준다.
     * 이때, DB 테이블에는 board_id 컬럼이 NOT NULL로 설정되어 있어서 임의로 어떠한 값을 넣거나, 에러를 발생시켜야 한다. (혹은, DB 테이블의 필드를 NULL로 변경할 수 있다)
     * */
    @Column(name = "board_id")
    private Long board_id;  // 생략 가능 --> jpa가 알아서 설정해준다!

    public void changeBoardId(Long board_id){
        // 이러한 메소드를 사용할 수도 있다! (필수는 아님, 사용하지 않을 경우 생성자 함수 등에서 초기 값을 설정해주어야 한다.)
        this.board_id = board_id;
    }
    /** 방법2 끝 */

    // 나머지 동일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comment(String content, Board board, User user){
        this.content = content;
        this.board = board;
        this.user = user;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
