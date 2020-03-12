package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.ToString;

/**
 * Author : 정태균
 * Part : All
 */

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
