package com.gabia.gyebalja.dto.education;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.dto.category.CategoryResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@Data
public class EducationAllResponseDto {
    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    private int totalHours;
    private EducationType type;
    private String place;
    private CategoryResponseDto category;  // Category category는 Entity를 그대로 노출 하는 것이므로

    @Builder
    public EducationAllResponseDto(Long id, String title, LocalDate startDate, LocalDate endDate, int totalHours, EducationType type, String place, CategoryResponseDto category) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalHours = totalHours;
        this.type = type;
        this.place = place;
        this.category = category;
    }
}
