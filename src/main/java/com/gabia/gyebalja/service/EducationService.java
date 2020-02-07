package com.gabia.gyebalja.service;


import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.dto.education.EducationRequestDto;
import com.gabia.gyebalja.dto.education.EducationResponseDto;
import com.gabia.gyebalja.repository.EducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor //final의 필드만 가지고 생성자를 만들어줌
@Transactional(readOnly = true)
@Service
public class EducationService {

    private final EducationRepository educationRepository;

    /** 등록 - education 한 건 (교육 등록) */
    @Transactional
    public Long save(EducationRequestDto educationRequestDto) {
        Long eduId = educationRepository.save(educationRequestDto.toEntity()).getId();

        return eduId;
    }

    /** 조회 - education 한 건 (상세페이지) */
    public EducationResponseDto findById(Long id) {
        Education education = educationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        return EducationResponseDto.builder()
                .id(education.getId())
                .title(education.getTitle())
                .content(education.getContent())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .totalHours(education.getTotalHours())
                .type(education.getType())
                .place(education.getPlace())
                .build();
    }

    /** 수정 - education 한 건 (상세페이지) */
    @Transactional
    public Long update(Long id, EducationRequestDto educationRequestDto) {
        Education education = educationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        education.changeEducation(educationRequestDto.getTitle(),educationRequestDto.getContent(),educationRequestDto.getStartDate(),educationRequestDto.getEndDate(), educationRequestDto.getTotalHours(), educationRequestDto.getType(), educationRequestDto.getPlace());

        return id;
    }

    /** 삭제 - education 한 건 (상세페이지) */
    @Transactional
    public Long delete(Long id) {
        educationRepository.deleteById(id);

        return id;
    }

    /** 조회 - education 전체 (페이징) */
    public Page<EducationResponseDto> findByUserId(Long id, Pageable pageable) {
        Page<Education> educationPage = educationRepository.findByUserId(id, pageable);
        Page<EducationResponseDto> educationDtoPage = educationPage.map(education -> new EducationResponseDto().builder()
                                                                                                                .id(education.getId())
                                                                                                                .title(education.getTitle())
                                                                                                                .content(education.getContent())
                                                                                                                .startDate(education.getStartDate())
                                                                                                                .endDate(education.getEndDate())
                                                                                                                .totalHours(education.getTotalHours())
                                                                                                                .type(education.getType())
                                                                                                                .place(education.getPlace())
                                                                                                                .build());
        return educationDtoPage;
    }

}
