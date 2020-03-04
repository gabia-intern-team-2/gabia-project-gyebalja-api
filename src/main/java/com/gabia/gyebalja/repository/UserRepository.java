package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    // 사용자가 속한 부서원 수 쿼리
    @Query("select count(u) from User u where u.department.id = :deptId")
    Long getUserNumberInDepartment(@Param("deptId") Long deptId);

    // 가비아 고유 사용자 번호로 조회 쿼리
    Optional<User> findUserByGabiaUserNo(Long gabiaUserNo);
}
