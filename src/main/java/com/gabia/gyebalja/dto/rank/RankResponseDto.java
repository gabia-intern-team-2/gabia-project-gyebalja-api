package com.gabia.gyebalja.dto.rank;

import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.user.UserResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author : 정태균
 * Part : All
 */

@NoArgsConstructor
@Data
public class RankResponseDto {

    private int rank;
    private int totalHour;
    private int totalCount;
    private UserResponseDto user;

    @Builder
    public RankResponseDto(int rank, int totalHour, int totalCount, User user) {
        this.rank = rank;
        this.totalHour = totalHour;
        this.totalCount = totalCount;
        this.user = new UserResponseDto(user);
    }
}
