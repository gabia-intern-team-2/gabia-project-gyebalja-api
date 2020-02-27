package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.EduTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EduTagRepository extends JpaRepository<EduTag, Long> {
  
    void deleteByEducationId(Long educationId);
  
}
