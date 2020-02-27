package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class StatisticsEducationCategoryResponseDto {
    private String categoryName;
    private Long totalNumber;

    public StatisticsEducationCategoryResponseDto(String categoryName, Long totalNumber){
        this.categoryName = categoryName;
        this.totalNumber = totalNumber;
    }
}
