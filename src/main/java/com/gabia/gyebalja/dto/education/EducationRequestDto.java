package com.gabia.gyebalja.dto.education;

import com.gabia.gyebalja.domain.EducationType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Author : 정태균
 * Part : All
 */

@NoArgsConstructor
@Data
public class EducationRequestDto {

    private  String title;
    private  String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalHours;
    private EducationType type;
    private String place;
    private Long userId;
    private Long categoryId;
    private String hashTag;


    @Builder
    public EducationRequestDto( String title, String content, LocalDate startDate, LocalDate endDate, int totalHours, EducationType type, String place, Long userId, Long categoryId, String hashTag ) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalHours = totalHours;
        this.type = type;
        this.place = place;
        this.userId = userId;
        this.categoryId = categoryId;
        this.hashTag = hashTag;
    }
}
