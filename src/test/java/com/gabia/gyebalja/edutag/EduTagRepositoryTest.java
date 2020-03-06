package com.gabia.gyebalja.edutag;

import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.EduTag;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.Tag;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EduTagRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.TagRepository;
import com.gabia.gyebalja.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
public class EduTagRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    EduTagRepository eduTagRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    @DisplayName("EduTag 저장 테스트(save)")
    public void saveTest() throws Exception {
        //given
        long beforeCnt = eduTagRepository.count();

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
                .password("1234")
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

        Tag tag = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag);

        EduTag eduTag = EduTag.builder()
                            .tag(tag)
                            .education(education)
                            .build();

        //when
        eduTagRepository.save(eduTag);
        em.clear();

        EduTag findEduTag = eduTagRepository.findById(eduTag.getId()).get();
        //then
        assertThat(findEduTag.getId()).isEqualTo(eduTag.getId());
        assertThat(findEduTag.getEducation().getId()).isEqualTo(eduTag.getEducation().getId());
        assertThat(findEduTag.getTag().getId()).isEqualTo(eduTag.getTag().getId());
        assertThat(eduTagRepository.count()).isEqualTo(beforeCnt+1);
    }

    @Test
    @DisplayName("EduTag 단건조회 테스트(findById)")
    public void findByIdTest() throws Exception {
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
                .password("1234")
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

        Tag tag = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag);

        EduTag eduTag = EduTag.builder()
                .tag(tag)
                .education(education)
                .build();
        eduTagRepository.save(eduTag);

        em.clear();

        //when
        EduTag findEduTag = eduTagRepository.findById(eduTag.getId()).get();

        //then
        assertThat(findEduTag.getId()).isEqualTo(eduTag.getId());
        assertThat(findEduTag.getTag().getId()).isEqualTo(eduTag.getTag().getId());
        assertThat(findEduTag.getEducation().getId()).isEqualTo(eduTag.getEducation().getId());
    }

    @Test
    @DisplayName("EduTag 전체 조회 테스트(findAll)")
    public void findAllTest() throws Exception {
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
                .password("1234")
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

        Tag tag = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag);

        EduTag eduTag = EduTag.builder()
                .tag(tag)
                .education(education)
                .build();
        eduTagRepository.save(eduTag);

        em.clear();

        //when
        List<EduTag> allEduTag = eduTagRepository.findAll();

        //then
        assertThat(allEduTag.size()).isEqualTo(1);
        assertThat(allEduTag.get(0).getId()).isEqualTo(eduTag.getId());
        assertThat(allEduTag.get(0).getTag().getId()).isEqualTo(eduTag.getTag().getId());
        assertThat(allEduTag.get(0).getEducation().getId()).isEqualTo(eduTag.getEducation().getId());
    }

    @Test
    @DisplayName("EduTag 갯수 테스트(count)")
    public void countTest() throws Exception {
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
                .password("1234")
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

        Tag tag = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag);

        EduTag eduTag = EduTag.builder()
                .tag(tag)
                .education(education)
                .build();
        eduTagRepository.save(eduTag);

        //when
        long count = eduTagRepository.count();

        //then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("EduTag 삭제 테스트(delete)")
    public void deleteTest() throws Exception {
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
                .password("1234")
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

        Tag tag = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag);

        EduTag eduTag = EduTag.builder()
                .tag(tag)
                .education(education)
                .build();
        eduTagRepository.save(eduTag);

        long beforeDeleteCnt = eduTagRepository.count();

        //when
        eduTagRepository.delete(eduTag);

        //then
        assertThat(eduTagRepository.count()).isEqualTo(beforeDeleteCnt-1);
        assertThat(eduTagRepository.findById(eduTag.getId())).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("EduTag 업데이트 테스트(update)")
    public void updateTest() throws Exception {
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
                .password("1234")
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

        Tag tag = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag);

        EduTag eduTag = EduTag.builder()
                .tag(tag)
                .education(education)
                .build();
        eduTagRepository.save(eduTag);

        Education updateEducation = Education.builder()
                .title("제목업데이트테스트")
                .content("내용업데이트테스트")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .totalHours(3)
                .type(EducationType.ONLINE)
                .place("가비아 5층")
                .category(category)
                .user(user)
                .build();
        educationRepository.save(updateEducation);

        long beforeUpdateCnt = eduTagRepository.count();

        //when
        eduTag.changeEducation(updateEducation);

        EduTag findEduTag = eduTagRepository.findById(eduTag.getId()).get();

        //then
        assertThat(findEduTag.getId()).isEqualTo(eduTag.getId());
        assertThat(findEduTag.getId()).isEqualTo(eduTag.getId());
        assertThat(findEduTag.getEducation().getTitle()).isEqualTo(updateEducation.getTitle());
        assertThat(eduTagRepository.count()).isEqualTo(beforeUpdateCnt);
    }

    @Test
    @DisplayName("EduTag 교육 ID로 삭제 테스트(deleteByEducationId)")
    public void deleteByEducationId() throws Exception {
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
                .password("1234")
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
        Education savedEducation = educationRepository.save(education);

        Tag tag = Tag.builder()
                .name("#Spring")
                .build();
        tagRepository.save(tag);

        EduTag eduTag = EduTag.builder()
                .tag(tag)
                .education(education)
                .build();
        eduTagRepository.save(eduTag);

        //when
        long beforeDeleteCnt = eduTagRepository.count();
        eduTagRepository.deleteByEducationId(savedEducation.getId());

        //then
        assertThat(eduTagRepository.count()).isEqualTo(beforeDeleteCnt-1);
        assertThat(eduTagRepository.findById(savedEducation.getId())).isEqualTo(Optional.empty());
    }

}
/**
 * given의 반복되는 값 세팅부분 클래스로 만들거나 @Before 사용하기.
 **/