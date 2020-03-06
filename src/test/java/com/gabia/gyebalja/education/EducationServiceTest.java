package com.gabia.gyebalja.education;

import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.Tag;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.education.EducationAllResponseDto;
import com.gabia.gyebalja.dto.education.EducationDetailResponseDto;
import com.gabia.gyebalja.dto.education.EducationRequestDto;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.TagRepository;
import com.gabia.gyebalja.repository.UserRepository;
import com.gabia.gyebalja.service.EducationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class EducationServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    EducationService educationService;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    TagRepository tagRepository;

    /**
     * 등록 - education 한 건 (자기교육 등록)
     */
    @Test
    @DisplayName("EducationService.postOneEducation() 테스트 (단건 저장)")
    public void postOneEducation() throws Exception {
        //given
        Category category = Category.builder()
                .name("개발자")
                .build();
        categoryRepository.save(category);

        Department department = Department.builder()
                .name("테스트팀")
                .depth(2)
                .parentDepartment(null)
                .build();
        departmentRepository.save(department);

        User user = User.builder()
                .email("test@gabia.com")
                .name("User1")
                .gender(GenderType.MALE)
                .phone("000-000-0000")
                .tel("111-111-1111")
                .positionId(123L)
                .positionName("팀원")
                .department(department)
                .profileImg("src/img")
                .build();
        userRepository.save(user);

        EducationRequestDto educationRequestDto = EducationRequestDto.builder()
                .title("test")
                .content("내용테스트")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .totalHours(3)
                .type(EducationType.ONLINE)
                .place("가비아 4층")
                .categoryId(category.getId())
                .userId(user.getId())
                .hashTag("#Spring #CSS #HTML")
                .build();

        //when
        Long saveId = educationService.postOneEducation(educationRequestDto);
        em.clear();
        Education findEducation = educationRepository.findById(saveId).get();

        //then
        assertThat(saveId).isEqualTo(findEducation.getId());
        assertThat(educationRequestDto.getTitle()).isEqualTo(findEducation.getTitle());
        assertThat(educationRequestDto.getContent()).isEqualTo(findEducation.getContent());
    }

    /**
     * 조회 - education 한 건 (상세페이지)
     */
    @Test
    @DisplayName("EducationApiController.getOneEducation() 테스트 (단건 조회)")
    public void getOneEducation() throws Exception {
        //given
        Category category = Category.builder()
                .name("개발자")
                .build();
        categoryRepository.save(category);

        Department department = Department.builder()
                .name("테스트팀")
                .depth(2)
                .parentDepartment(null)
                .build();
        departmentRepository.save(department);

        User user = User.builder()
                .email("test@gabia.com")
                .name("User1")
                .gender(GenderType.MALE)
                .phone("000-000-0000")
                .tel("111-111-1111")
                .positionId(123L)
                .positionName("팀원")
                .department(department)
                .profileImg("src/img")
                .build();
        userRepository.save(user);

        Education education = Education.builder()
                .title("제목테스트")
                .content("내용테스트")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .totalHours(3)
                .type(EducationType.ONLINE)
                .place("가비아 4층")
                .category(category)
                .user(user)
                .build();

        educationRepository.save(education);
        em.clear();

        //when
        EducationDetailResponseDto findEducation = educationService.getOneEducation(education.getId());

        //then
        assertThat(findEducation.getId()).isEqualTo(education.getId());
        assertThat(findEducation.getTitle()).isEqualTo(education.getTitle());
        assertThat(findEducation.getContent()).isEqualTo(education.getContent());
    }

    /**
     * 수정 - education 한 건 (상세페이지)
     */
    @Test
    @DisplayName("EducationService.putOneEducation() 테스트 (단건 수정)")
    public void putOneEducation() throws Exception {
        //given
        String updateTitle = "업데이트 타이틀";
        String updateContent = "업데이트 컨텐트";
        Category category = Category.builder()
                .name("개발자")
                .build();
        categoryRepository.save(category);

        Tag tag = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag);

        Department department = Department.builder()
                .name("테스트팀")
                .depth(2)
                .parentDepartment(null)
                .build();
        departmentRepository.save(department);

        User user = User.builder()
                .email("test@gabia.com")
                .name("User1")
                .gender(GenderType.MALE)
                .phone("000-000-0000")
                .tel("111-111-1111")
                .positionId(123L)
                .positionName("팀원")
                .department(department)
                .profileImg("src/img")
                .build();
        userRepository.save(user);

        EducationRequestDto educationRequestDto = EducationRequestDto.builder()
                .title("제목테스트")
                .content("내용테스트")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .totalHours(3)
                .type(EducationType.ONLINE)
                .place("가비아 4층")
                .categoryId(category.getId())
                .userId(user.getId())
                .hashTag(tag.getName().toString())
                .build();
        Long saveId = educationService.postOneEducation(educationRequestDto);

        //when
        educationRequestDto.setTitle(updateTitle);
        educationRequestDto.setContent(updateContent);
        Long updateId = educationService.putOneEducation(saveId, educationRequestDto);
        Education findUpdateEducation = educationRepository.findById(updateId).get();

        //then
        assertThat(updateId).isEqualTo(saveId);
        assertThat(findUpdateEducation.getTitle()).isEqualTo(updateTitle);
        assertThat(findUpdateEducation.getContent()).isEqualTo(updateContent);
    }

    /**
     * 삭제 - education 한 건 (상세페이지)
     */
    @Test
    @DisplayName("EducationService.deleteOneEducation() 테스트 (단건 삭제)")
    public void deleteOneEducation() throws Exception {
        //given
        Category category = Category.builder()
                .name("개발자")
                .build();
        categoryRepository.save(category);

        Department department = Department.builder()
                .name("테스트팀")
                .depth(2)
                .parentDepartment(null)
                .build();
        departmentRepository.save(department);

        User user = User.builder()
                .email("test@gabia.com")
                .name("User1")
                .gender(GenderType.MALE)
                .phone("000-000-0000")
                .tel("111-111-1111")
                .positionId(123L)
                .positionName("팀원")
                .department(department)
                .profileImg("src/img")
                .build();
        userRepository.save(user);

        Education education = Education.builder()
                .title("제목테스트")
                .content("내용테스트")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .totalHours(3)
                .type(EducationType.ONLINE)
                .place("가비아 4층")
                .category(category)
                .user(user)
                .build();

        educationRepository.save(education);
        long beforeDeleteCnt = educationRepository.count();

        //when
        Long deleteId = educationService.deleteOneEducation(education.getId());

        //then
        assertThat(educationRepository.count()).isEqualTo(beforeDeleteCnt-1);
        assertThat(educationRepository.findById(deleteId)).isEqualTo(Optional.empty());
    }

    /**
     * 조회 - education 전체 (페이징)
     */
    @Test
    @DisplayName("EducationService.getAllEducationByUserId 테스트 (전체 조회)")
    public void getAllEducationByUserId() throws Exception {
        //given
        int totalNum = 30;
        Pageable pageable = PageRequest.of(0,10, Sort.Direction.DESC, "id");
        Category category = Category.builder()
                .name("개발자")
                .build();
        categoryRepository.save(category);

        Tag tag = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag);

        Department department = Department.builder()
                .name("테스트팀")
                .depth(2)
                .parentDepartment(null)
                .build();
        departmentRepository.save(department);

        User user = User.builder()
                .email("test@gabia.com")
                .name("User1")
                .gender(GenderType.MALE)
                .phone("000-000-0000")
                .tel("111-111-1111")
                .positionId(123L)
                .positionName("팀원")
                .department(department)
                .profileImg("src/img")
                .build();
        userRepository.save(user);

        String title = "api test";
        String content = "내용 테스트";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        int totalHours = 3;
        EducationType type = EducationType.ONLINE;
        String place = "가비아 3층";

        EducationRequestDto educationRequestDto = EducationRequestDto.builder()
                .title(title)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .totalHours(totalHours)
                .type(type)
                .place(place)
                .userId(user.getId())
                .categoryId(category.getId())
                .hashTag(tag.getName().toString())
                .build();

        for(int i =0; i<totalNum; i++) {
            educationService.postOneEducation(educationRequestDto);
        }

        //when
        List<EducationAllResponseDto> allEducationByUserId = educationService.getAllEducationByUserId(user.getId(), pageable);

        //then
        assertThat(allEducationByUserId.size()).isEqualTo(10); //한 페이지당 데이터 10개
    }

}
