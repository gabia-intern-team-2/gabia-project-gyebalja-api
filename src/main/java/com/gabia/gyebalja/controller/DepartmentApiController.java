package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.department.DepartmentResponseDto;
import com.gabia.gyebalja.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author : 이현재
 * Part : All
 */

@RequiredArgsConstructor
@Api(value = "DepartmentApiController V1")
@RestController
public class DepartmentApiController {

    private final DepartmentService departmentService;

    /** 조회 - department (한 부서) */
    @ApiOperation(value = "getOneDepartment : 조회 - department (한 부서)", notes = "부서 한 건 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/departments/{departmentId}")
    public CommonJsonFormat getOneDepartment(@PathVariable("departmentId") Long departmentId){
        DepartmentResponseDto response = departmentService.getOneDepartment(departmentId);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 등록 - department (한 부서) */

    /** 수정 - department (한 부서) */

    /** 삭제 - department (한 부서) */

    /** 조회 - department (전체) */
    @ApiOperation(value = "getAllDepartment : 조회 - department (전체)", notes = "부서 전체 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/departments")
    public CommonJsonFormat getAllDepartment(){
        List<DepartmentResponseDto> response = departmentService.getAllDepartment();

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }
}
