package com.gabia.gyebalja.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "edu_tag")
public class EduTag extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Education과 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_id")
    private Education education;

    //Tag와의 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public EduTag(Education education, Tag tag) {
        this.education = education;
        this.tag = tag;
    }

    public void changeEducation(Education education) {
        this.education = education;
    }
    public void changeTag(Tag tag) {
        this.tag = tag;
    }
}
