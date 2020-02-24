package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Education;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

//@Repository
//public interface StatisticsRepository<T extends Statistics, IdType extends Serializable> extends CrudRepository<T, IdType> {
public interface StatisticsRepository extends JpaRepository<Education, Long>{
    /** 통계 - 메인 화면 */

    /** 연도별 교육 건수, 시간 */
    // 방법 1
    @Query("select count(e), sum(e.totalHours) from Education e where substring(e.startDate, 1, 4) in :year")
    List<ArrayList<Long>> getMainStatisticsWithYear1(@Param("year") List<String> year);

    // 방법 2
    @Query("select substring(e.startDate, 1, 4) as year, count(e) as totalEducationNumberOfEmployees, sum(e.totalHours) as totalEducationHourOfEmployees " +
            "from Education e " +
            "group by substring(e.startDate, 1, 4) " +
            "order by substring(e.startDate, 1, 4) desc")
    List<ArrayList<String>> getMainStatisticsWithYear2(Pageable pageable);

    /** 월별 교육 건수, 시간 */
    // 방법 1
    @Query("select substring(e.startDate, 1 ,7) as month, count(e) as totalEducationNumberOfEmployees, sum(e.totalHours) as totalEducationHourOfEmployees " +
            "from Education e " +
            "group by substring(e.startDate, 1, 7) " +
            "order by substring(e.startDate, 1, 7) desc")
    List<ArrayList<String>> getMainStatisticsWithMonth(Pageable pageable);

//    // 방법 2
//    @Query("select substring(e.startDate, 1, 7) , count(e), sum(e.totalHours) " +
//            "from Education e " +
//            "where e.startDate > DATE_SUB(now(), INTERVAL 12 MONTH) " +
//            "group by substring(e.startDate, 1, 7) " +
//            "order by substring(e.startDate, 1, 7) desc")
//
//    // 방법 3
//    List<Education> findByStartDateBetween(final LocalDate startDate, final LocalDate endDate);

    /** 카테고리 TOP n */
    @Query(value = "select c.name, count(e) from Education e join e.category c group by c.id order by count(e) desc")
    List<ArrayList<String>> getMainStatisticsWithCategory(Pageable pageable);

    /** 태그 TOP n*/
    @Query("select t.name, count(et) from EduTag et join et.tag t group by et.tag order by count(et) desc")
    List<ArrayList<String>> getMainStatisticsWithTag(Pageable pageable);
}
