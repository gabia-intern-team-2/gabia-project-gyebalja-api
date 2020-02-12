package com.gabia.gyebalja.service;

import com.gabia.gyebalja.common.HashTagRegularExpression;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.EduTag;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.Tag;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.category.CategoryResponseDto;
import com.gabia.gyebalja.dto.education.EducationAllResponseDto;
import com.gabia.gyebalja.dto.education.EducationDetailResponseDto;
import com.gabia.gyebalja.dto.education.EducationRequestDto;
import com.gabia.gyebalja.dto.edutag.EduTagResponseDto;
import com.gabia.gyebalja.exception.NotExistCategoryException;
import com.gabia.gyebalja.exception.NotExistUserException;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.EduTagRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.TagRepository;
import com.gabia.gyebalja.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor //final의 필드만 가지고 생성자를 만들어줌
@Transactional(readOnly = true)
@Service
public class EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EduTagRepository eduTagRepository;
    private final TagRepository tagRepository;

    /** 등록 - education 한 건 (교육 등록) */
    @Transactional
    public Long postOneEducation(EducationRequestDto educationRequestDto) {
        Optional<User> findUser = userRepository.findById(educationRequestDto.getUserId());

        if(!findUser.isPresent())
            throw new NotExistUserException("존재하지 않는 회원입니다.");

        Optional<Category> findCategory = categoryRepository.findById(educationRequestDto.getCategoryId());

        if(!findCategory.isPresent())
            throw new NotExistCategoryException("존재하지 않는 카테고리입니다.");

        Education education =  Education.builder()
                                        .title(educationRequestDto.getTitle())
                                        .content(educationRequestDto.getContent())
                                        .startDate(educationRequestDto.getStartDate())
                                        .endDate(educationRequestDto.getEndDate())
                                        .totalHours(educationRequestDto.getTotalHours())
                                        .type(educationRequestDto.getType())
                                        .place(educationRequestDto.getPlace())
                                        .user(findUser.get())
                                        .category(findCategory.get())
                                        .build();

        Long eduId = educationRepository.save(education).getId();

        //해시태그 삽입 로직
        HashTagRegularExpression hashTagRegularExpression = new HashTagRegularExpression();
        ArrayList<String> extractHashTagList = hashTagRegularExpression.getExtractHashTag(educationRequestDto.getHashTag());

        for (String s : extractHashTagList) {
            Optional<Tag> findHashTag = tagRepository.findHashTagByName(s);

            if(!findHashTag.isPresent()){
                Tag tag = tagRepository.save(Tag.builder().name(s).build());
                EduTag eduTag = EduTag.builder().education(education).tag(tag).build();
                eduTagRepository.save(eduTag);
            }else{
                EduTag eduTag = EduTag.builder().education(education).tag(findHashTag.get()).build();
                eduTagRepository.save(eduTag);
            }
        }

        return eduId;
    }

    /** 조회 - education 한 건 (상세페이지) */
    public EducationDetailResponseDto getOneEducation(Long id) {
        //N+1 문제 해결을 위한 fetch join 사용 (성능 최적화)
        Optional<Education> education = educationRepository.findEducationDetail(id);

        if(!education.isPresent())
            throw new NotExistCategoryException("존재하지 않는 교육입니다.");

        CategoryResponseDto categoryResponseDto = CategoryResponseDto.builder()
                                                                        .id(education.get().getCategory().getId())
                                                                        .name(education.get().getCategory().getName())
                                                                        .build();


        List<EduTagResponseDto> tagList = education.get().getEduTags().stream().map(EduTagResponseDto::new)
                                                                        .collect(toList());

        return EducationDetailResponseDto.builder()
                .id(education.get().getId())
                .title(education.get().getTitle())
                .content(education.get().getContent())
                .startDate(education.get().getStartDate())
                .endDate(education.get().getEndDate())
                .totalHours(education.get().getTotalHours())
                .type(education.get().getType())
                .place(education.get().getPlace())
                .category(categoryResponseDto)
                .eduTag(tagList)
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
    public List<EducationAllResponseDto> getAllEducationByUserId(Long id, Pageable pageable) {

        Optional<User> findUser = userRepository.findById(id);

        if(!findUser.isPresent())
            throw new NotExistUserException("존재하지 않는 회원입니다.");

        List<Education> educationPage = educationRepository.findEducationByUserId(id, pageable);

        List<EducationAllResponseDto> educationDtoPage = educationPage.stream().map(e -> new EducationAllResponseDto().builder()
                                                                                                            .id(e.getId())
                                                                                                            .title(e.getTitle())
                                                                                                            .startDate(e.getStartDate())
                                                                                                            .endDate(e.getEndDate())
                                                                                                            .totalHours(e.getTotalHours())
                                                                                                            .type(e.getType())
                                                                                                            .place(e.getPlace())
                                                                                                            .category(CategoryResponseDto.builder().id(e.getCategory().getId()).name(e.getCategory().getName()).build())
                                                                                                            .build()).collect(Collectors.toList());
        return educationDtoPage;

    }

}
