package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author : 정태균
 * Part : All
 */

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
