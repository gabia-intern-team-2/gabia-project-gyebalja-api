package com.gabia.gyebalja.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Department {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(nullable = false)
    private int depth;

    @OneToMany(mappedBy = "department")
    private List<User> users = new ArrayList<>();

    // 셀프 참조
    // 참조: https://siyoon210.tistory.com/82
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Department parentDepartment;

    @OneToMany(mappedBy = "parentDepartment")
    private List<Department> childDepartments;
}
