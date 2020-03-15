package com.gabia.gyebalja.dto.likes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Author : 이현재
 * Part : All
 */

@NoArgsConstructor
@ToString
@Getter
public class LikesResponseDto {

    private boolean isLikes;

    public LikesResponseDto(boolean isLikes){
        this.isLikes = isLikes;
    }
}
