package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.EduTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EduTagRepository extends JpaRepository<EduTag, Long> {
    @Modifying
    @Transactional
    @Query("delete from EduTag et where et.education.id = :eduId")
    void deleteByEduId(@Param("eduId") Long eduId);

}
