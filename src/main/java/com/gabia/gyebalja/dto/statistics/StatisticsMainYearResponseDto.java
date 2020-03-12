package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.ToString;

/**
 * Author : 이현재
 * Part : All
 */

@ToString
@Getter
public class StatisticsMainYearResponseDto {
    private String[] years;
    private long[] totalEducationTime;
    private long[] totalEducationCount;

    public StatisticsMainYearResponseDto(String[] years, long[] totalEducationTime, long[] totalEducationCount){
        this.years = years;
        this.totalEducationTime = totalEducationTime;
        this.totalEducationCount = totalEducationCount;
    }
}
