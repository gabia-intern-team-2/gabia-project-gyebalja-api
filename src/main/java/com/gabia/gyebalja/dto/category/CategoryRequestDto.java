package com.gabia.gyebalja.dto.category;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryRequestDto {
    private String name;

    @Builder
    public CategoryRequestDto(String name) {
        this.name = name;
    }

}
