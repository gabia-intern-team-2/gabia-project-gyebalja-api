package com.gabia.gyebalja.dto.edutag;

import com.gabia.gyebalja.domain.EduTag;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author : 정태균
 * Part : All
 */

@NoArgsConstructor
@Data
public class EduTagResponseDto {
    //클라이언트 입장에서는 관계 테이블인 EduTag의 정보보다는 Tag의 정보가 필요함으로
    //API 상에서도 Depth를 줄일 수있고 필요한 tagid와 tagName만 반환하도록 EduTagResponseDto 작성
    private Long tagId;
    private String tagName;

    @Builder
    public EduTagResponseDto(EduTag eduTag) {
        this.tagId = eduTag.getTag().getId();
        this.tagName = eduTag.getTag().getName();
    }
}
