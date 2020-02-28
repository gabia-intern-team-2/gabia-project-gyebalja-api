package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@NoArgsConstructor
@ToString
@Getter
public class StatisticsMainMonthResponseDto {
    private ArrayList<String> months;
    private ArrayList<Long> totalEducationTime;
    private ArrayList<Long> totalEducationCount;

    public StatisticsMainMonthResponseDto(ArrayList<String> months, ArrayList<Long> totalEducationTime, ArrayList<Long> totalEducationCount){
        this.months = months;
        this.totalEducationTime = totalEducationTime;
        this.totalEducationCount = totalEducationCount;
    }
}
