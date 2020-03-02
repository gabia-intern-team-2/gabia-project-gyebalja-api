package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class StatisticsMainMonthResponseDto {
    private String year;
    private String[] months = new String[12];
    private long[] totalEducationTime;
    private long[] totalEducationCount;

    public StatisticsMainMonthResponseDto(String year, String[] months, long[] totalEducationTime, long[] totalEducationCount){
        this.year = year;
        this.months = months;
        this.totalEducationTime = totalEducationTime;
        this.totalEducationCount = totalEducationCount;
    }
}
