package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.rank.RankResponseDto;
import com.gabia.gyebalja.service.RankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author : 정태균
 * Part : All
 */

@RequiredArgsConstructor
@Api(value = "RankApiController V1")
@RestController
public class RankApiController {

    private final RankService rankService;

    /** 조회 - rank 전체 (해당 부서) */
    @ApiOperation(value = "getRankByDeptId : 조회 - rank 전체 (해당 부서)", notes = "해당 부서 팀원들의 순위와 팀원 정보를 ( 시간 -> 건수 -> 이름순)으로 리턴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/ranks")
    public CommonJsonFormat getRankByDeptId(@RequestParam("deptId") Long deptId) {
        List<RankResponseDto> response = rankService.getRankByDeptId(deptId);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(), response);
    }
}
