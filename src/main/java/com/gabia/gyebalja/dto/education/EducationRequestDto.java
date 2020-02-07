package com.gabia.gyebalja.dto.education;

import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private User user;
    private Category category;

    @Builder
    public EducationRequestDto( String title, String content, LocalDate startDate, LocalDate endDate, int totalHours, EducationType type, String place, User user, Category category ) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalHours = totalHours;
        this.type = type;
        this.place = place;
        this.user = user;
        this.category = category;
    }

    public Education toEntity() {
        return Education.builder()
                .title(title)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .totalHours(totalHours)
                .type(type)
                .place(place)
                .user(user)
                .category(category)
                .build();
    }
}
