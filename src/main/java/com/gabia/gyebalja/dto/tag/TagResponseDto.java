package com.gabia.gyebalja.dto.tag;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TagResponseDto {
    private Long id;
    private String name;

    @Builder
    public TagResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
