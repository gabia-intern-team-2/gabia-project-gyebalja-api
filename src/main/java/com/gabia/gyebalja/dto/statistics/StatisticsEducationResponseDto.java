package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class StatisticsEducationResponseDto {
    private StatisticsEducationCategoryResponseDto categoryData;
    private StatisticsEducationHourResponseDto hoursData;
    private StatisticsEducationMonthResponseDto monthlyData;
    private StatisticsEducationRankResponseDto rankData;
    private StatisticsEducationTagResponseDto tagData;

    public StatisticsEducationResponseDto(StatisticsEducationCategoryResponseDto categoryData, StatisticsEducationHourResponseDto hoursData, StatisticsEducationMonthResponseDto monthlyData, StatisticsEducationRankResponseDto rankData, StatisticsEducationTagResponseDto tagData) {
        this.categoryData = categoryData;
        this.hoursData = hoursData;
        this.monthlyData = monthlyData;
        this.rankData = rankData;
        this.tagData = tagData;
    }
}
