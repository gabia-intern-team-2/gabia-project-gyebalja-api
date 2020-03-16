package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.dto.department.DepartmentResponseDto;
import com.gabia.gyebalja.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : 이현재
 * Part : All
 */

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    /** 조회 - department (한 부서) */
    public DepartmentResponseDto getOneDepartment(Long departmentId){
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto(department);

        return departmentResponseDto;
    }

    /** 등록 - department (한 부서) */

    /** 수정 - department (한 부서) */

    /** 삭제 - department (한 부서) */

    /** 조회 - department (전체) */
    public List<DepartmentResponseDto> getAllDepartment(){
        List<DepartmentResponseDto> departmentResponseDtos = departmentRepository.findAll().stream().map(department -> new DepartmentResponseDto(department)).collect(Collectors.toList());

        return departmentResponseDtos;
    }
}
