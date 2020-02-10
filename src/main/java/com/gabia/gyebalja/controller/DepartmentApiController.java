package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.department.DepartmentResponseDto;
import com.gabia.gyebalja.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DepartmentApiController {

    private final DepartmentService departmentService;

    /** 조회 - department (한 부서) */
    @GetMapping("/api/v1/departments/{departmentId}")
    public CommonJsonFormat getOneDepartment(@PathVariable("departmentId") Long departmentId){
        DepartmentResponseDto response = departmentService.findById(departmentId);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 등록 - department (한 부서) */

    /** 수정 - department (한 부서) */

    /** 삭제 - department (한 부서) */
}
