package com.gabia.gyebalja.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(length = 15, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserGender gender;

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

    //Department와 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department department;

    @Builder
    public User(String email, String password, String name, UserGender gender, String phone, String tel, Long positionId, String positionName, String profileImg, Department department) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.tel = tel;
        this.positionId = positionId;
        this.positionName = positionName;
        this.profileImg = profileImg;
        this.department = department;
    }


    /**
     * 연관관계 메서드 세팅
     * 유저의 부서가 변경된 경우
     */
//    public void changeDept(Department department) {
//        this.department = department;
//
//        department.getUsers().add(this);
//    }

    public void changePassword(String password) {
        this.password = password;
    }



}
