package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.EduTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface EduTagRepository extends JpaRepository<EduTag, Long> {
    @Modifying
    @Transactional
    @Query("delete from EduTag et where et.education.id = :eduId")
    void deleteByEduId(@Param("eduId") Long eduId);

    /** 태그 TOP n*/
    @Query("select t.name, count(et) from EduTag et join et.tag t group by et.tag order by count(et) desc")
    List<ArrayList<String>> getMainStatisticsWithTag(Pageable pageable);
}
