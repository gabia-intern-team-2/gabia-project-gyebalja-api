package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.ToString;

/**
 * Author : 정태균
 * Part : All
 */

@ToString
@Getter
public class StatisticsEducationMonthResponseDto {
    private String year;
    private String[] months;
    private long[] userEducationTimes;
    private long[] userEducationCounts;

    public StatisticsEducationMonthResponseDto(String year, String[] months, long[] userEducationTimes, long[] userEducationCounts){
        this.year = year;
        this.months = months;
        this.userEducationTimes = userEducationTimes;
        this.userEducationCounts = userEducationCounts;
    }
}
