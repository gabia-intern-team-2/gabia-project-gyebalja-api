package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.statistics.StatisticsMainCategoryResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainMonthResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainTagResponseDto;
import com.gabia.gyebalja.dto.statistics.StatisticsMainYearResponseDto;
import com.gabia.gyebalja.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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
}
