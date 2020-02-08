package com.gabia.gyebalja.education;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.domain.Category;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.Education;
import com.gabia.gyebalja.domain.EducationType;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.education.EducationRequestDto;
import com.gabia.gyebalja.dto.education.EducationResponseDto;
import com.gabia.gyebalja.repository.CategoryRepository;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.EducationRepository;
import com.gabia.gyebalja.repository.UserRepository;
import com.gabia.gyebalja.service.EducationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EducationControllerTest {
    @Autowired
    private EducationService educationService;
    @Autowired
    private EducationRepository educationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @PersistenceContext
    EntityManager em;

    @AfterEach
    public void cleanUp() {
        System.out.println("============================ cleanUp()");
        //TestRestTemplate은 Transaction을 못 걸어주므로 테스트 종료 후 수동으로 디비 초기화
        this.educationRepository.deleteAll();
        this.userRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.departmentRepository.deleteAll();
    }

    /**
     * 등록 - education 한 건 (자기교육 등록)
     */
    @Test
    @DisplayName("EducationApiController.postOneEducation() 테스트 (단건 저장)")
    public void postOneEducation() throws Exception {
        //given
        String url = "http://localhost:" + port + "/api/v1/educations";

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
                .user(user)
                .category(category)
                .build();

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.postForEntity(url, educationRequestDto, CommonJsonFormat.class);
        EducationResponseDto findEducation = educationService.findById(Long.parseLong(responseEntity.getBody().getResponse().toString()));
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(200);
        assertThat(responseEntity.getBody().getMessage()).isEqualTo("success");
        assertThat(responseEntity.getBody().getResponse().toString()).isEqualTo(findEducation.getId().toString());

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
                 .user(user)
                 .category(category)
                 .build();

         Long saveId = educationService.save(educationRequestDto);
         String url = "http://localhost:" + port + "/api/v1/educations/" + saveId;
         //when
         ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class); //response 부분이 LinkedHashMap -> 제네릭은 런타임에 타입정보가 사라짐
         LinkedHashMap response = (LinkedHashMap) responseEntity.getBody().getResponse();
         //then
         assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
         assertThat(responseEntity.getBody().getCode()).isEqualTo(200);
         assertThat(responseEntity.getBody().getMessage()).isEqualTo("success");
         assertThat(response.get("id").toString()).isEqualTo(saveId.toString());
         assertThat(response.get("title")).isEqualTo(title);
     }

    /**
     * 수정 - education 한 건 (상세페이지)
     */
    @Test
    @DisplayName("EducationApiController.putOneEducation() 테스트 (단건 수정)")
    public void putOneEducation() throws Exception {
        // given
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
                .user(user)
                .category(category)
                .build();

        Long saveId = educationService.save(educationRequestDto);

        String url = "http://localhost:" + port + "/api/v1/educations/" + saveId;
        String updateTitle = "제목 변경";
        String updateContent = "내용 변경";
        EducationRequestDto updateRequestDto = EducationRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .startDate(startDate)
                .endDate(endDate)
                .totalHours(totalHours)
                .type(type)
                .place(place)
                .user(user)
                .category(category)
                .build();

        HttpEntity<EducationRequestDto> requestEntity = new HttpEntity<>(updateRequestDto);
        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, CommonJsonFormat.class);
        // then
        Education education = educationRepository.findById(saveId).orElseThrow(() -> new IllegalArgumentException("해당 데이터 없음"));
        assertThat(responseEntity.getBody().getCode()).isEqualTo(200);
        assertThat(responseEntity.getBody().getMessage()).isEqualTo("success");
        assertThat(responseEntity.getBody().getResponse().toString()).isEqualTo(saveId.toString());
        assertThat(education.getTitle()).isEqualTo(updateTitle);
        assertThat(education.getContent()).isEqualTo(updateContent);
    }

    /**
     * 삭제 - education 한 건 (상세페이지)
     */
    @Test
    @DisplayName("EducationApiController.deleteOneEducation() 테스트 (단건 삭제)")
    public void deleteOneEducation() throws Exception {
        // given
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
                .user(user)
                .category(category)
                .build();

        Long saveId = educationService.save(educationRequestDto);

        String url = "http://localhost:" + port + "/api/v1/educations/" + saveId;
        // when
        restTemplate.delete(url);
        // then
        assertThat(educationRepository.findById(saveId)).isEqualTo(Optional.empty());
    }

    /**
     * 조회 - education 전체 (페이징)
     */
    @Test
    @DisplayName("EducationApiController.getAllEducationByUserId 테스트 (전체 조회)")
    public void getAllEducationByUserId() throws Exception {
        // given
        int totalNum = 30;
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
                .user(user)
                .category(category)
                .build();

        for(int i =0; i<totalNum; i++) {
            educationService.save(educationRequestDto);
        }
        String url = "http://localhost:" + port + "/api/v1/users/" + user.getId() + "/educations";

        // when
        CommonJsonFormat requestEntity = restTemplate.getForObject(url, CommonJsonFormat.class);
        // then
        LinkedHashMap castResult = (LinkedHashMap) requestEntity.getResponse();
        ArrayList result = (ArrayList) castResult.get("content");
        assertThat(result.size()).isEqualTo(10); //추후 검증로직 추가 예정
    }

}



