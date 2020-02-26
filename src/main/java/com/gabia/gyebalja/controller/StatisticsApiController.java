package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.education.EducationDetailResponseDto;
import com.gabia.gyebalja.dto.statistics.*;
import com.gabia.gyebalja.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StatisticsApiController {

    private final StatisticsService statisticsService;

    /** 조회 - 메인 화면 */
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
    @GetMapping("/api/v1/statistics/education/users/{userId}")
    public CommonJsonFormat getEducationStatistics(@PathVariable("userId") Long id) {
        StatisticsEducationMonthResponseDto statisticsEducationMonthResponseDto = statisticsService.getEducationStatisticsWithMonth(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(),statisticsEducationMonthResponseDto);
    }

    /** 조회 - 교육 화면 */
    @GetMapping("/api/v1/statistics/category/users/{userId}")
    public CommonJsonFormat getCategoryStatistics(@PathVariable("userId") Long id) {
        StatisticsEducationCategoryResponseDto statisticsEducationCategoryResponseDto = statisticsService.getEducationStatisticsWithCategory(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(),statisticsEducationCategoryResponseDto);

    }

    /** 조회 - 교육 화면 */
    @GetMapping("/api/v1/statistics/tags/users/{userId}")
    public CommonJsonFormat getTagStatistics(@PathVariable("userId") Long id) {
        StatisticsEducationTagResponseDto statisticsEducationTagResponseDto = statisticsService.getEducationStatisticsWithTag(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(),statisticsEducationTagResponseDto);

    }

    /** 조회 - 교육 화면 */
    @GetMapping("/api/v1/statistics/hours/users/{userId}")
    public CommonJsonFormat getHoursStatistics(@PathVariable("userId") Long id) {
        StatisticsEducationHourResponseDto statisticsEducationHourResponseDto = statisticsService.getEducationStatisticsWithHour(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(),statisticsEducationHourResponseDto);

    }

    /** 조회 - 교육 화면 */
    @GetMapping("/api/v1/statistics/rank/users/{userId}")
    public CommonJsonFormat getRankStatistics(@PathVariable("userId") Long id) {
        StatisticsEducationRankResponseDto statisticsEducationRankResponseDto = statisticsService.getEducationStatisticsWithRank(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(),statisticsEducationRankResponseDto);

    }
}
