package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@NoArgsConstructor
@ToString
@Getter
public class StatisticsMainCategoryResponseDto {
    private ArrayList<String> categories;
    private ArrayList<Long> totalCategoryCount;

    public StatisticsMainCategoryResponseDto(ArrayList<String> categories, ArrayList<Long> totalCategoryCount){
        this.categories = categories;
        this.totalCategoryCount = totalCategoryCount;
    }
}
