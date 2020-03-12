package com.gabia.gyebalja.dto.tag;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author : 정태균
 * Part : All
 */

@NoArgsConstructor
@Data
public class TagRequestDto {
    private String name;

    @Builder
    public TagRequestDto(String name) {
        this.name = name;
    }
}
