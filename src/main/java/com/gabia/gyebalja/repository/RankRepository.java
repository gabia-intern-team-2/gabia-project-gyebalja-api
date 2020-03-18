package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : 정태균
 * Part : All
 */

public interface RankRepository extends JpaRepository<User, Long> {
    /** 부서별 랭킹 */
    @Query("select sum(e.totalHours), count(e), u.id as totalHours " +
            "from User u left join u.educations e " +
            "where u.department.id = :deptId " +
            "group by u.id " +
            "order by sum(e.totalHours) desc, count(e) desc, u.name ")
    List<ArrayList<String>> getRankByDeptId(@Param("deptId") Long deptId);
}
