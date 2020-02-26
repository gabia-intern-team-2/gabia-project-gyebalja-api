package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@NoArgsConstructor
@ToString
@Getter
public class StatisticsEducationMonthResponseDto {

    private String year;
    private ArrayList<String> months;
    private ArrayList<Long> userEducationTimes;
    private ArrayList<Long> userEducationCounts;

    public StatisticsEducationMonthResponseDto(String year, ArrayList<String> months, ArrayList<Long> userEducationTimes, ArrayList<Long> userEducationCounts){
        this.year = year;
        this.months = months;
        this.userEducationTimes = userEducationTimes;
        this.userEducationCounts = userEducationCounts;
    }
}
