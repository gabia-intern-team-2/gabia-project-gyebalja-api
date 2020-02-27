package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.ToString;

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
