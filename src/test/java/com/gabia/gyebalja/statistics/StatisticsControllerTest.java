package com.gabia.gyebalja.statistics;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author : 이현재
 * Part : main
 * Author : 정태균
 * Part : education
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application-test.yml")
public class StatisticsControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @LocalServerPort
    private int port;

    public StatisticsControllerTest() {
        // Interceptor 해제
        System.setProperty("spring.profiles.active.test", "true");
    }

    @Test
    public void getMainStatistics() {
        // given
        String url = "http://localhost:" + port + "/api/v1/statistics/main";

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
    }

    @Test
    public void getEducationStatistics() throws Exception {
        //given
        Long gabiaUserNo = 12345L;

        Department department = Department.builder()
                .name("Team1")
                .depth(2)
                .parentDepartment(null)
                .build();

        Department saveDepartment = departmentRepository.save(department);

        User user = User.builder()
                .gabiaUserNo(gabiaUserNo)
                .email("test@gabia.com")
                .name("User1")
                .engName("Ted")
                .gender(GenderType.MALE)
                .phone("000-0000-0000")
                .tel("111-1111-1111")
                .positionId(23L)
                .positionName("인턴")
                .department(saveDepartment)
                .profileImg("src/img")
                .build();

        User saveUser = userRepository.save(user);

        String url = "http://localhost:" + port + "/api/v1/statistics/education/users/"+saveUser.getId();

        //when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
    }
}
