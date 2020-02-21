package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@NoArgsConstructor
@ToString
@Getter
public class StatisticsMainYearResponseDto {
    private ArrayList<String> years;
    private ArrayList<Long> totalEducationHourOfEmployees;
    private ArrayList<Long> totalEducationNumberOfEmployees;

    public StatisticsMainYearResponseDto(ArrayList<String> years, ArrayList<Long> totalEducationHourOfEmployees, ArrayList<Long> totalEducationNumberOfEmployees){
        this.years = years;
        this.totalEducationHourOfEmployees = totalEducationHourOfEmployees;
        this.totalEducationNumberOfEmployees = totalEducationNumberOfEmployees;
    }
}
