package com.gabia.gyebalja.dto.tag;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TagRequestDto {
    private String name;

    @Builder
    public TagRequestDto(String name) {
        this.name = name;
    }
}
