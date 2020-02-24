package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Education;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
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

    /** 통계 - 메인 화면 */
    /** 연도별 교육 건수, 시간 */
    // 방법 1
    @Query("select count(e), sum(e.totalHours) from Education e where substring(e.startDate, 1, 4) in :year")
    List<ArrayList<Long>> getMainStatisticsWithYear1(@Param("year") List<String> year);

    // 방법 2
    @Query("select substring(e.startDate, 6, 2) as year, count(e) as totalEducationNumberOfEmployees, sum(e.totalHours) as totalEducationHourOfEmployees " +
            "from Education e " +
            "group by substring(e.startDate, 6, 2) " +
            "order by substring(e.startDate, 6, 2) desc")
    List<ArrayList<Long>> getMainStatisticsWithYear2(Pageable pageable);

    /** 월별 교육 건수, 시간 */
    // 방법 1
    @Query("select substring(e.startDate, 1 ,7) as month, count(e) as totalEducationNumberOfEmployees, sum(e.totalHours) as totalEducationHourOfEmployees " +
            "from Education e " +
            "group by substring(e.startDate, 1, 7) " +
            "order by substring(e.startDate, 1, 7) desc")
    List<ArrayList<String>> getMainStatisticsWithMonth(Pageable pageable);

    /** 카테고리 TOP n */
    @Query(value = "select c.name, count(e) from Education e join e.category c group by c.id order by count(e) desc")
    List<ArrayList<String>> getMainStatisticsWithCategory(Pageable pageable);
}
