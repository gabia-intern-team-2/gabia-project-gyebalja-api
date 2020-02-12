package com.gabia.gyebalja.service;

import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.category.CategoryResponseDto;
import com.gabia.gyebalja.dto.education.EducationRequestDto;
import com.gabia.gyebalja.dto.education.EducationResponseDto;
import com.gabia.gyebalja.exception.NotExistCategoryException;
import com.gabia.gyebalja.exception.NotExistUserException;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.EduTagRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor //final의 필드만 가지고 생성자를 만들어줌
@Transactional(readOnly = true)
@Service
public class EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EduTagRepository eduTagRepository;

    /** 등록 - education 한 건 (교육 등록) */
    @Transactional
    public Long postOneEducation(EducationRequestDto educationRequestDto) {
        Optional<User> findUser = userRepository.findById(educationRequestDto.getUserId());

        if(!findUser.isPresent())
            throw new NotExistUserException("존재하지 않는 회원입니다.");

        Optional<Category> findCategory = categoryRepository.findById(educationRequestDto.getCategoryId());

        if(!findCategory.isPresent())
            throw new NotExistCategoryException("존재하지 않는 카테고리입니다.");

        //추후에 Tag들 삽입해주는 로직 추가 예정
        //ArrayList<Long> tagIds = educationRequestDto.getTagId();

        Long eduId = educationRepository.save(Education.builder()
                .title(educationRequestDto.getTitle())
                .content(educationRequestDto.getContent())
                .startDate(educationRequestDto.getStartDate())
                .endDate(educationRequestDto.getEndDate())
                .totalHours(educationRequestDto.getTotalHours())
                .type(educationRequestDto.getType())
                .place(educationRequestDto.getPlace())
                .user(findUser.get())
                .category(findCategory.get())
                .build()).getId();

        return eduId;
    }

    /** 조회 - education 한 건 (상세페이지) */
    public EducationResponseDto getOneEducation(Long id) {
        Education education = educationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        // 추후 성능 최적화 대상. 현재는 education 조회 쿼리한번, category 조회 쿼리한번 발생(페치 조인으로 해결 예정) ManyToOne 관계라 페이징처리도 가능.
        CategoryResponseDto categoryResponseDto = CategoryResponseDto.builder()
                                                                        .id(education.getCategory().getId())
                                                                        .name(education.getCategory().getName())
                                                                        .build();
        //강제 초기화
        return EducationResponseDto.builder()
                .id(education.getId())
                .title(education.getTitle())
                .content(education.getContent())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .totalHours(education.getTotalHours())
                .type(education.getType())
                .place(education.getPlace())
                .category(categoryResponseDto)
                .build();
    }

    /** 수정 - education 한 건 (상세페이지) */
    @Transactional
    public Long putOneEducation(Long id, EducationRequestDto educationRequestDto) {
        Education education = educationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        education.changeEducation(educationRequestDto.getTitle(),educationRequestDto.getContent(),educationRequestDto.getStartDate(),educationRequestDto.getEndDate(), educationRequestDto.getTotalHours(), educationRequestDto.getType(), educationRequestDto.getPlace());

        return id;
    }

    /** 삭제 - education 한 건 (상세페이지) */
    @Transactional
    public Long deleteOneEducation(Long id) {
        educationRepository.deleteById(id);

        return id;
    }

    /** 조회 - education 전체 (페이징) */
    public Page<EducationResponseDto> getAllEducationByUserId(Long id, Pageable pageable) {
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
