package com.gabia.gyebalja.dto.education;

import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;

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
    private ArrayList<Long> tagId = new ArrayList<>();


    @Builder
    public EducationRequestDto( String title, String content, LocalDate startDate, LocalDate endDate, int totalHours, EducationType type, String place, Long userId, Long categoryId, ArrayList<Long> tagId ) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalHours = totalHours;
        this.type = type;
        this.place = place;
        this.userId = userId;
        this.categoryId = categoryId;
        this.tagId = tagId;
    }

}
