package com.gabia.gyebalja.repository;

import com.gabia.gyebalja.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author : 이현재
 * Part : All
 */

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
