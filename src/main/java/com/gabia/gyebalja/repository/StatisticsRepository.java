package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Education;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Education, Long>{
    /** 통계 - 메인 화면 */
    /** 연도별 교육 건수, 시간 */
    @Query("select substring(e.startDate, 1, 4) as year, sum(e.totalHours) as totalEducationHourOfEmployees, count(e) as totalEducationNumberOfEmployees " +
            "from Education e " +
            "where substring(e.startDate, 1, 4) > :currentYear " +
            "group by substring(e.startDate, 1, 4)")
    List<ArrayList<String>> getMainStatisticsWithYear(@Param("currentYear") String currentYear);

    /** 월별 교육 건수, 시간 */
    @Query("select substring(e.startDate, 6 ,2) as month, sum(e.totalHours) as totalEducationHourOfEmployees, count(e) as totalEducationNumberOfEmployees " +
            "from Education e " +
            "where substring(e.startDate, 1, 4) = :currentYear " +
            "group by substring(e.startDate, 6, 2)")
    List<ArrayList<String>> getMainStatisticsWithMonth(@Param("currentYear") String currentYear);

    /** 카테고리 TOP n */
    @Query("select c.name, count(e) " +
            "from Education e join e.category c " +
            "group by c.id " +
            "order by count(e) desc")
    List<ArrayList<String>> getMainStatisticsWithCategory(Pageable pageable);

    /** 태그 TOP n*/
    @Query("select t.name, count(et) " +
            "from EduTag et join et.tag t " +
            "group by t " +
            "order by count(et) desc")
    List<ArrayList<String>> getMainStatisticsWithTag(Pageable pageable);


    /** 통계 - 개인 교육 관리 화면 */
    /** 월별 교육 건수, 시간 (당해년도)*/
    @Query(" select substring(e.startDate, 6 ,2) as month, sum(e.totalHours) as EducationHoursOfUser, count(e) as EducationNumbersOfUser " +
            " from Education e " +
            " where e.user.id = :userId and substring(e.startDate, 1, 4) = :currentYear" +
            " group by substring(e.startDate, 6, 2) " +
            " order by substring(e.startDate, 6, 2) ")
    List<ArrayList<String>> getEducationStatisticsWithMonth(@Param("userId") Long userId, @Param("currentYear") String currentYear);

    /** 개인 최대 관심 카테고리 (누적)*/
    @Query(" select c.name as categoryName, count(e) as totalNumber from Education e join e.category c  where e.user.id = :userId group by c.id order by count(e) desc ")
    List<ArrayList<String>> getEducationStatisticsWithCategory(@Param("userId") Long userId, Pageable pageable);

    /** 개인 TOP 3 태그 (누적)*/
    @Query("select t.name, count(et) from Education e join e.eduTags et join et.tag t where e.user.id = :userId group by et.tag order by count(et) desc")
    List<ArrayList<String>> getEducationStatisticsWithTag(@Param("userId") Long userId, Pageable pageable);

    /** 개인 교육시간 vs 회사 전체 교육시간 평균 (당해년도) */
    // 올해 개인 총 교육시간
    @Query("select sum(e.totalHours) from Education e where e.user.id = :userId and substring(e.startDate, 1, 4) = :currentYear")
    Long getEducationStatisticsWithIndividualTotalHours(@Param("userId") Long userId, @Param("currentYear") String currentYear);

    // 올해 회사 총 교육시간
    @Query("select sum(e.totalHours) from Education e where substring(e.startDate, 1, 4) = :currentYear")
    Long getEducationStatisticsWithCompanyTotalHours(@Param("currentYear") String currentYear);

    /** 개인 부서 내 등수 (당해년도) */
    // 부서원들의 시간 합계 조회
    @Query("select sum(e.totalHours) as totalHours " +
            "from Education e join e.user u " +
            "where u.department.id = :deptId and substring(e.startDate, 1, 4) = :currentYear " +
            "group by u.id" +
            " order by sum(e.totalHours) desc")
    ArrayList<Long> getEducationStatisticsWithRank(@Param("deptId") Long deptId, @Param("currentYear") String currentYear);
}
