package com.gabia.gyebalja.dto.category;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author : 정태균
 * Part : All
 */

@NoArgsConstructor
@Data
public class CategoryRequestDto {
    private String name;

    @Builder
    public CategoryRequestDto(String name) {
        this.name = name;
    }
}
