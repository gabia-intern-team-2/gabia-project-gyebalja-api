package com.gabia.gyebalja.dto.education;

import com.gabia.gyebalja.domain.EducationType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class EducationResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalHours;
    private EducationType type;
    private String place;

    @Builder
    public EducationResponseDto(Long id, String title, String content, LocalDate startDate, LocalDate endDate, int totalHours, EducationType type, String place ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalHours = totalHours;
        this.type = type;
        this.place = place;
    }
}
