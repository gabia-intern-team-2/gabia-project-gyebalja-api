package com.gabia.gyebalja.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //프록시가 이생성자를 사용함 다른생성자를 사용하려면 기본생성자가 필요한데 Protected로 기본생성자를 만들어줌.
@Entity
public class Education {

    //교육 테이블 id (PK)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 DB에 위임
    private Long id;

    //교육 테이블 제목
    @Column(nullable = false)
    private  String title;

    //교육 테이블 내용
    @Column(columnDefinition = "TEXT")
    private  String content;

    //교육 시작날짜
    @Column(name = "start_date")
    private LocalDate startDate;
    //교육 종료날짜
    @Column(name = "end_date")
    private LocalDate endDate;
    //교육 시간
    @Column(name = "total_hours")
    private int totalHours;

    //교육 유형 (ONLINE, OFFLINE)
    @Enumerated(EnumType.STRING) //ORIGINAL로 하면 유지보수성이 떨어짐으로 STRING으로 사용
    private EducationType type;

    //교육 장소
    private String place;


    //생성일, 수정일
//    private LocalDateTime createdDate;
//    private LocalDateTime modifiedDate;

    //User와 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //Board와 연관관계
    @OneToMany(mappedBy = "education")
    private List<Board> boards = new ArrayList<>();

    //Category와 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    //EduTag와 연관관계
    @OneToMany(mappedBy = "education")
    private List<EduTag> eduTags = new ArrayList<>();


    @Builder
    public Education(String title, String content, LocalDate startDate, LocalDate endDate, int totalHours, EducationType type, String place, User user, Category category ) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalHours = totalHours;
        this.type = type;
        this.place = place;
        this.user = user;
        this.category = category;
    }


    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }


//    @Builder
//    public Education(String title, String content, LocalDate startDate, LocalDate endDate, int totalHours, EducationType type, String place) {
//        this.title = title;
//        this.content = content;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.totalHours = totalHours;
//        this.type = type;
//        this.place = place;
//    }

//
//    //교육내용 변경 메서드
//    public void changeEducation(String title, String content, LocalDateTime startDate, LocalDateTime endDate, int totalHours, EducationType type, String place ) {
//        this.title = title;
//        this.content = content;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.totalHours = totalHours;
//        this.type = type;
//        this.place = place;
//    }


}