package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public class StatisticsEducationRankResponseDto {
    private int rank;
    private Long teamMemberNumber;

    public StatisticsEducationRankResponseDto(int rank, Long teamMemberNumber) {
        this.rank = rank;
        this.teamMemberNumber = teamMemberNumber;
    }
}
