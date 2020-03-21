package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.rank.RankResponseDto;
import com.gabia.gyebalja.service.RankService;
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
@RestController
public class RankApiController {

    private final RankService rankService;

    /** 조회 - rank 전체 (해당 부서) */
    @GetMapping("/api/v1/ranks")
    public CommonJsonFormat getRankByDeptId(@RequestParam("deptId") Long deptId) {
        List<RankResponseDto> response = rankService.getRankByDeptId(deptId);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(), response);
    }
}
