package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Education;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education,Long> {
    //Page<Education> findByUserId(Long id, Pageable pageble);
}
