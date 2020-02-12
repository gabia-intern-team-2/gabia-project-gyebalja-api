package com.gabia.gyebalja.dto.education;

import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.dto.category.CategoryResponseDto;
import com.gabia.gyebalja.dto.edutag.EduTagResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
public class EducationDetailResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private int totalHours;
    private EducationType type;
    private String place;
    private CategoryResponseDto category;  // Category category는 Entity를 그대로 노출 하는 것이므로
    private List<EduTagResponseDto> eduTags; // Dto 내부속에서도 Entity와의 관계를 다 끊어주기 위해 Dto로 받음

    @Builder
    public EducationDetailResponseDto(Long id, String title, String content, LocalDate startDate, LocalDate endDate, int totalHours, EducationType type, String place, CategoryResponseDto category, List<EduTagResponseDto> eduTag) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalHours = totalHours;
        this.type = type;
        this.place = place;
        this.category = category;
        this.eduTags = eduTag;
    }

}
