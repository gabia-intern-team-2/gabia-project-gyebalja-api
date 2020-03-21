package com.gabia.gyebalja.rank;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.repository.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author : 정태균
 * Part : All
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application-test.yml")
public class RankControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department;

    @LocalServerPort
    private int port;

    public RankControllerTest() {
        // Interceptor 해제
        System.setProperty("spring.profiles.active.test", "true");
    }

    @AfterEach
    public void cleanUp() {
        this.departmentRepository.deleteAll();
    }

    @Test
    @DisplayName("부서원 랭킹 조회 테스트(Controller)")
    public void getRankByDeptId() {
        // given
        this.department = Department.builder()
                .name("테스트팀")
                .depth(0)
                .parentDepartment(null)
                .build();
        Department savedDept = departmentRepository.save(this.department);
        String url = "http://localhost:" + port + "/api/v1/ranks?deptId="+ savedDept.getId();

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
    }
}
