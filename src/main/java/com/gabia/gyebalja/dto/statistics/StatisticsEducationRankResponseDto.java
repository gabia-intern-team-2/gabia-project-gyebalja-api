package com.gabia.gyebalja.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@NoArgsConstructor
@ToString
@Getter
public class StatisticsEducationRankResponseDto {

    private int rank;
    private int teamMemberNumber;

    public StatisticsEducationRankResponseDto(int rank, int teamMemberNumber) {
        this.rank = rank;
        this.teamMemberNumber = teamMemberNumber;
    }
}
