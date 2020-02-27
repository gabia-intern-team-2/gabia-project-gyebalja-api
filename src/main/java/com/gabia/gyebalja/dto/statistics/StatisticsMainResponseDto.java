package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class StatisticsMainResponseDto {
    private StatisticsMainYearResponseDto yearlyData;
    private StatisticsMainMonthResponseDto monthlyData;
    private StatisticsMainCategoryResponseDto categoryData;
    private StatisticsMainTagResponseDto tagData;

    public StatisticsMainResponseDto(StatisticsMainYearResponseDto yearlyData, StatisticsMainMonthResponseDto monthlyData, StatisticsMainCategoryResponseDto categoryData, StatisticsMainTagResponseDto tagData) {
        this.yearlyData = yearlyData;
        this.monthlyData = monthlyData;
        this.categoryData = categoryData;
        this.tagData = tagData;
    }
}
