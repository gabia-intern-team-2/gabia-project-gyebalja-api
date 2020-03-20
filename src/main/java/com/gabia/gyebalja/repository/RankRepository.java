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
    @Query("select sum(e.totalHours), count(e), u " +
            "from User u left join Education e on (u.id = e.user.id and substring(e.startDate, 1, 4) = :currentYear) " +
            "where u.department.id = :deptId " +
            "group by u.id " +
            "order by sum(e.totalHours) desc, count(u) desc, u.name asc")
    List<ArrayList<Object>> getRankByDeptId(@Param("deptId") Long deptId,  @Param("currentYear") String currentYear);
}
