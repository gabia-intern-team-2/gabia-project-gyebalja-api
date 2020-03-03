package com.gabia.gyebalja.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성 전략중 데이터베이스 위임 'IDENTITY' 사용 (mysql)
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(name = "eng_name")
    private String engName;
    /**
     * enum 사용 시 Default가 ORDINARY인데 이것을 사용시 확장성에서도 문제가 생기며 유연하지 않음
     * 꼭 EnumType.STRING 사용!
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderType gender;

    @Column(length = 15)
    private String phone;
    @Column(length = 15)
    private String tel;

    @Column(name = "position_id", nullable = false)
    private Long positionId;
    @Column(name = "position_name", length = 30, nullable = false)
    private String positionName;
    @Column(name = "profile_img")
    private String profileImg;
    @Column(name = "gabia_user_no", nullable = false)
    private Long  gabiaUserNo;

    //Department와 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department department;

    @Builder
    public User(Long gabiaUserNo, String email, String password, String name, String engName,GenderType gender, String phone, String tel, Long positionId, String positionName, String profileImg, Department department) {
        this.gabiaUserNo = gabiaUserNo;
        this.email = email;
        this.password = password;
        this.name = name;
        this.engName = engName;
        this.gender = gender;
        this.phone = phone;
        this.tel = tel;
        this.positionId = positionId;
        this.positionName = positionName;
        this.profileImg = profileImg;
        this.department = department;
    }

    //비밀번호 변경 비즈니스 로직
    public void changePassword(String password) {
        this.password = password;
    }

}
