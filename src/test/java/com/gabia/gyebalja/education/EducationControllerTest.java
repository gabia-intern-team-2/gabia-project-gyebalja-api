package com.gabia.gyebalja.education;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.domain.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

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
         ObjectMapper mapper = new ObjectMapper();

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
         //System.out.println("saveId = " + saveId);
         String url = "http://localhost:" + port + "/api/v1/educations/"+ saveId;
         //when
         ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);
         //then
         assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
         assertThat(responseEntity.getBody().getCode()).isEqualTo(200);
         assertThat(responseEntity.getBody().getMessage()).isEqualTo("success");
         System.out.println("responseEntity = " +  responseEntity.getBody().getResponse().toString());
         System.out.println("educationRequestDto = " + educationRequestDto);

     }


}
