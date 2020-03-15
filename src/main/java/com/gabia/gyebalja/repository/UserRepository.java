package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Author : 정태균
 * Part : All
 */

public interface UserRepository extends JpaRepository<User,Long> {
    // 사용자가 속한 부서원 수 쿼리
    Long countByDepartmentId(Long DeptId);

    // 가비아 고유 사용자 번호로 조회 쿼리
    Optional<User> findUserByGabiaUserNo(Long gabiaUserNo);
}
