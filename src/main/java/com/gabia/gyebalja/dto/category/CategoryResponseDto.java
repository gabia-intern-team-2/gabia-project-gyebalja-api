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
public class CategoryResponseDto {
    private Long id;
    private String name;

    @Builder
    public CategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
