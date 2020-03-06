package com.gabia.gyebalja.education;

import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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


/**
 * @DisplayName : Junit 5부터 사용할 수 있는 어노테이션
 * -> 테스트 코드 네이밍으론 테스트하고자 하는 의미를 전달하기 어려움
 * -> 코드에 대한 설명을 문자열로 대체할 수있음 : 실제 테스트 케이스 이름으로 표시 됨.
 */

@Transactional
@DataJpaTest
class EducationRepositoryTest {

    @PersistenceContext
    EntityManager em;


    @Autowired
    EducationRepository educationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DepartmentRepository departmentRepository;


    @Test
    @DisplayName("Education 저장 테스트(save)")
    public void saveTest() throws Exception {
        //given
        /**
         * 저장 전 데이터의 갯수와 저장 후 저장전 갯수 +1 맞는지 검증하기 위해 
         */
        Long beforeCnt = educationRepository.count();
        
        
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

        //when
        Education savedEducation = educationRepository.save(education);
        Education findEducation = educationRepository.findById(savedEducation.getId()).get();
        
        //then
        assertThat(findEducation).isEqualTo(education); //jpa 엔티티 동일성 보장 검증
        assertThat(findEducation.getId()).isEqualTo(savedEducation.getId());
        assertThat(findEducation.getTitle()).isEqualTo("제목테스트");
        assertThat(findEducation.getContent()).isEqualTo("내용테스트");
        assertThat(beforeCnt+1).isEqualTo(educationRepository.count());

    }

    @Test
    @DisplayName("Education 단건조회 테스트(findById")
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

        em.flush();  //영속성 컨텍스트 초기화
        em.clear();
        //when
        Education findEducation = educationRepository.findById(education.getId()).get();

        //then
        assertThat(findEducation.getTitle()).isEqualTo("제목테스트");
        assertThat(findEducation.getContent()).isEqualTo("내용테스트");
    }

    @Test
    @DisplayName("Education 전체 조회 테스트(findAll)")
    public void findAllTest() throws Exception {
        //given
            for(int i=0 ; i<2 ; i++) {
                Category category = Category.builder()
                        .name("개발자"+i)
                        .build();
                categoryRepository.save(category);

                Department department = Department.builder()
                        .name("테스트팀"+i)
                        .depth(2+i)
                        .parentDepartment(null)
                        .build();
                departmentRepository.save(department);

                User user = User.builder()
                        .email("test"+i+"@gabia.com")
                        .name("User"+i)
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
                        .title("제목테스트"+i)
                        .content("내용테스트"+i)
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now())
                        .totalHours(3)
                        .type(EducationType.ONLINE)
                        .place("가비아 4층"+i)
                        .category(category)
                        .user(user)
                        .build();

                educationRepository.save(education);

            }

            em.flush();
            em.clear();
        //when
            List<Education> allEducation = educationRepository.findAll();
        //then
            assertThat(allEducation.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Education 갯수 테스트(count)")
    public void countTest() throws Exception {
        //given
        for(int i=0 ; i<2 ; i++) {
            Category category = Category.builder()
                    .name("개발자"+i)
                    .build();
            categoryRepository.save(category);

            Department department = Department.builder()
                    .name("테스트팀"+i)
                    .depth(2+i)
                    .parentDepartment(null)
                    .build();
            departmentRepository.save(department);

            User user = User.builder()
                    .email("test"+i+"@gabia.com")
                    .name("User"+i)
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
                    .title("제목테스트"+i)
                    .content("내용테스트"+i)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .totalHours(3)
                    .type(EducationType.ONLINE)
                    .place("가비아 4층"+i)
                    .category(category)
                    .user(user)
                    .build();

            educationRepository.save(education);

        }
        //when
            //count 함수 호출
            long count = educationRepository.count();
        //then
            //데이터를 두개 넣어줬으니 카운트 결과와 2가 같은지 검증
            assertThat(count).isEqualTo(2);

    }

    @Test
    @DisplayName("Education 삭제 테스트(delete)")
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

        Long beforeDeleteNumOfData = educationRepository.count();
        //when
        educationRepository.delete(education);

        //then
        assertThat(educationRepository.count()).isEqualTo(beforeDeleteNumOfData-1);
        assertThat(educationRepository.findById(education.getId())).isEqualTo(Optional.empty());

    }

    @Test
    @DisplayName("Education 업데이트 테스트(update)")
    public void updateTest() throws Exception {
        //given
        String updateTitle = "제목 업데이트";
        String updateContent = "본문 업데이트";

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

        Education savedEducation = educationRepository.save(education);
        //더티 체킹 발생
        savedEducation.changeTitle(updateTitle);
        savedEducation.changeContent(updateContent);

        //영속성 컨텍스트 초기화
        em.flush();
        em.clear();

        //when
        Optional<Education> findUpdateEducation = educationRepository.findById(savedEducation.getId());
        //then

        assertThat(findUpdateEducation.get().getId()).isEqualTo(savedEducation.getId());
        assertThat(findUpdateEducation.get().getTitle()).isEqualTo(updateTitle);
        assertThat(findUpdateEducation.get().getContent()).isEqualTo(updateContent);

    }

    @Test
    @DisplayName("Education UserId로 조회 테스트(findEducationByUserId)")
    public void findEducationByUserId() throws Exception {
        //given
        int inputNum = 10;
        Pageable pageable = PageRequest.of(0,10, Sort.Direction.DESC, "id");
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
                .name("User")
                .gender(GenderType.MALE)
                .phone("000-000-0000")
                .tel("111-111-1111")
                .positionId(123L)
                .positionName("팀원")
                .department(department)
                .profileImg("src/img")
                .build();
        User savedUser = userRepository.save(user);

        for(int i=0 ; i<inputNum; i++) {
            Education education = Education.builder()
                    .title("제목테스트"+i)
                    .content("내용테스트"+i)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .totalHours(3)
                    .type(EducationType.ONLINE)
                    .place("가비아 4층"+i)
                    .category(category)
                    .user(user)
                    .build();

            educationRepository.save(education);

        }
        em.flush();
        em.clear();
        //when
        List<Education> educationList = educationRepository.findEducationByUserId(savedUser.getId(), pageable);
        //then
        assertThat(educationList.size()).isEqualTo(inputNum);
        assertThat(educationList.get(0).getUser().getId()).isEqualTo(savedUser.getId());
    }

    @Test
    @DisplayName("Education 상세 조회 테스트(페치조인) (findEducationDetail)")
    public void findEducationDetail() throws Exception {
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

        Education savedEducation = educationRepository.save(education);

        em.flush();
        em.clear();

        //when
        Optional<Education> educationDetail = educationRepository.findEducationDetail(savedEducation.getId());
        //then
        assertThat(educationDetail.get().getId()).isEqualTo(savedEducation.getId());
        assertThat(educationDetail.get().getTitle()).isEqualTo(savedEducation.getTitle());
        assertThat(educationDetail.get().getContent()).isEqualTo(savedEducation.getContent());
        assertThat(educationDetail.get().getCategory().getName()).isEqualTo(category.getName());

    }
}
/**
 * 검토 사항 : given에 값을 생성해주는 코드가 반복적으로 사용됨 -> 모듈화 해줄 것
 **/