package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

/**
 * Author : 정태균
 * Part : All
 */

@ToString
@Getter
public class StatisticsEducationTagResponseDto {
    private ArrayList<String> tagNames;
    private ArrayList<Long> totalCount;

    public StatisticsEducationTagResponseDto(ArrayList<String> tagNames, ArrayList<Long> totalCount){
        this.tagNames = tagNames;
        this.totalCount = totalCount;
    }
}