package com.gabia.gyebalja.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString(of = {"id", "name", "depth"})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(nullable = false)
    private int depth;

    // 셀프 참조
    // 참조: https://siyoon210.tistory.com/82
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Department parentDepartment;

    @Builder
    public Department(String name, int depth, Department parentDepartment){
        this.name = name;
        this.depth = depth;
        this.parentDepartment = parentDepartment;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changeDepth(int depth){
        this.depth = depth;
    }
}
