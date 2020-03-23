package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.statistics.StatisticsEducationCategoryResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsEducationHourResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsEducationMonthResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsEducationRankResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsEducationResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsEducationTagResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainCategoryResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainMonthResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainTagResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainYearResponseDto;
import com.gabia.gyebalja.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author : 이현재
 * Part : getMainStatistics()
 * Author : 정태균
 * Part : getEducationStatistics()
 */

@RequiredArgsConstructor
@Api(value = "StatisticsApiController V1")
@RestController
public class StatisticsApiController {

    private final StatisticsService statisticsService;

    /** 조회 - 메인 화면 */
    @ApiOperation(value = "getMainStatistics : 조회 - 메인화면의 통계", notes = "메인 화면 통계 4개에 대한 요청 (회사 년도별 추이, 회사 카테고리 Top 3, 회사 월별 추이, 회사 태그 Top 3")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/statistics/main")
    public CommonJsonFormat getMainStatistics() {
        StatisticsMainYearResponseDto statisticsMainYearResponseDto = statisticsService.getMainStatisticsWithYear();
        StatisticsMainMonthResponseDto statisticsMainMonthResponseDto = statisticsService.getMainStatisticsWithMonth();
        StatisticsMainCategoryResponseDto statisticsMainCategoryResponseDto = statisticsService.getMainStatisticsWithCategory();
        StatisticsMainTagResponseDto statisticsMainTagResponseDto = statisticsService.getMainStatisticsWithTag();

        StatisticsMainResponseDto response = new StatisticsMainResponseDto(statisticsMainYearResponseDto, statisticsMainMonthResponseDto, statisticsMainCategoryResponseDto, statisticsMainTagResponseDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }

    /** 조회 - 교육 화면 */
    @ApiOperation(value = "getEducationStatistics : 조회 - 사용자 교육관리 화면의 통계", notes = "교육 화면 통계 5개에 대한 요청 (개인 월별 추이, 개인 태그 Top 3, 개인 부서내 순위, 개인 최고관심 카테고리, 회사 vs 나")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/statistics/education/users/{userId}")
    public CommonJsonFormat getEducationStatistics(@PathVariable("userId") Long id) {
        StatisticsEducationCategoryResponseDto statisticsEducationCategoryResponseDto = statisticsService.getEducationStatisticsWithCategory(id);
        StatisticsEducationHourResponseDto statisticsEducationHourResponseDto = statisticsService.getEducationStatisticsWithHour(id);
        StatisticsEducationMonthResponseDto statisticsEducationMonthResponseDto = statisticsService.getEducationStatisticsWithMonth(id);
        StatisticsEducationRankResponseDto statisticsEducationRankResponseDto = statisticsService.getEducationStatisticsWithRank(id);
        StatisticsEducationTagResponseDto statisticsEducationTagResponseDto = statisticsService.getEducationStatisticsWithTag(id);

        StatisticsEducationResponseDto response = new StatisticsEducationResponseDto(statisticsEducationCategoryResponseDto, statisticsEducationHourResponseDto, statisticsEducationMonthResponseDto, statisticsEducationRankResponseDto, statisticsEducationTagResponseDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }
}
