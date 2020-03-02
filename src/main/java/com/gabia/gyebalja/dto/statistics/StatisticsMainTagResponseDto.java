package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
public class StatisticsMainTagResponseDto {
    private ArrayList<String> names;
    private ArrayList<Long> totalTagCount;

    public StatisticsMainTagResponseDto(ArrayList<String> names, ArrayList<Long> totalTagCount){
        this.names = names;
        this.totalTagCount = totalTagCount;
    }
}
