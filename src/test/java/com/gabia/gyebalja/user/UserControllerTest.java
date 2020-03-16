package com.gabia.gyebalja.user;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.user.UserRequestDto;
import com.gabia.gyebalja.dto.user.UserResponseDto;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.UserRepository;
import com.gabia.gyebalja.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Author : 정태균
 * Part : All
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application-test.yml")
public class UserControllerTest {

    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private Department department;
    private User user;

    @BeforeEach
    public void setUp() {
        departmentRepository.save(this.department);
    }

    @AfterEach
    public void cleanUp() {
        this.departmentRepository.deleteAll();
        this.userRepository.deleteAll();
    }

    @Autowired
    public UserControllerTest() {
        // Interceptor 해제
        System.setProperty("spring.profiles.active.test", "true");

        // Department
        this.department = Department.builder()
                .name("테스트팀")
                .depth(0)
                .parentDepartment(null)
                .build();

        // User
        this.user = User.builder()
                .email("gabiaUser@gabia.com")
                .name("가비아")
                .engName("Gabia")
                .gabiaUserNo(12345L)
                .gender(GenderType.MALE)
                .phone("010-2345-5678")
                .tel("02-2345-5678")
                .positionId(5L)
                .positionName("직원")
                .department(this.department)
                .profileImg("src/img")
                .build();

    }

    @Test
    @DisplayName("UserApiController.postOneUser() 테스트 (단건 등록)")
    public void postOneUser() throws Exception {
        //given
        String url = "http://localhost:" + port + "/api/v1/users";

        UserRequestDto userRequestDto = new UserRequestDto(this.user);

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.postForEntity(url, userRequestDto, CommonJsonFormat.class);
        UserResponseDto findUserDto = userService.getOneUser(Long.parseLong(responseEntity.getBody().getResponse().toString()));

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(responseEntity.getBody().getResponse().toString()).isEqualTo(findUserDto.getId().toString());
    }

    @Test
    @DisplayName("UserApiController.getOneUser() 테스트 (단건 조회)")
    public void getOneUser() throws Exception {
        //given
        UserRequestDto userRequestDto = new UserRequestDto(this.user);
        Long savedId = userService.postOneUser(userRequestDto);
        String url = "http://localhost:" + port + "/api/v1/users/" + savedId;

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);
        LinkedHashMap response = (LinkedHashMap) responseEntity.getBody().getResponse();

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(response.get("id").toString()).isEqualTo(savedId.toString());
        assertThat(response.get("name")).isEqualTo(userRequestDto.getName());
    }

    @Test
    @DisplayName("UserApiController.putOneUser() 테스트 (단건 수정)")
    public void putOneUser() throws Exception {
        //given
        UserRequestDto userRequestDto = new UserRequestDto(this.user);
        Long savedId = userService.postOneUser(userRequestDto);
        String url = "http://localhost:" + port + "/api/v1/users/" + savedId;
        String updateEngName = "updateEngName";

        //when
        User updateUser = User.builder()
                .email("gabiaUser@gabia.com")
                .name("가비아")
                .engName(updateEngName)
                .gabiaUserNo(12345L)
                .gender(GenderType.MALE)
                .phone("010-2345-5678")
                .tel("02-2345-5678")
                .positionId(5L)
                .positionName("직원")
                .department(this.department)
                .profileImg("src/img")
                .build();

        UserRequestDto updateRequestDto = new UserRequestDto(updateUser);
        HttpEntity<UserRequestDto> requestEntity = new HttpEntity<>(updateRequestDto);
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, CommonJsonFormat.class);
        User findUser = userRepository.findById(savedId).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        //then
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(responseEntity.getBody().getResponse().toString()).isEqualTo(savedId.toString());
        assertThat(findUser.getEngName()).isEqualTo(updateEngName);
    }
}
