package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Education;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education,Long> {
    //사용자의 교육목록을 가져오기 위한 메서드
    //Page<Education> findByUserId(Long id, Pageable pageble);

    //교육 상세조회 (N+1 문제 해결을 위한 fetch 조인 (Category는 필수 입력항목이라 inner join , Tag는 필수가 아니기때문에 left join))
    @Query("select distinct e from Education e join fetch e.category c left join fetch e.eduTags et left join fetch et.tag t where e.id = :educationId")
    Optional<Education> findEducationDetail(@Param("educationId") Long educationId);

    //사용자의 교육목록을 가져오기 위한 메서드
    @Query("select e from Education e join fetch e.category c where e.user.id = :userId")
    List<Education> findEducationByUserId(@Param("userId") Long userId, Pageable pageable);
}
