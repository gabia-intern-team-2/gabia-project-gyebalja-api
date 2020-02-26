package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@NoArgsConstructor
@ToString
@Getter
public class StatisticsEducationHourResponseDto {

    private Long individualHour;
    private Long averageCompHour;

    public StatisticsEducationHourResponseDto(Long individualHour, Long averageCompHour){
        this.individualHour = individualHour;
        this.averageCompHour = averageCompHour;
    }
}
