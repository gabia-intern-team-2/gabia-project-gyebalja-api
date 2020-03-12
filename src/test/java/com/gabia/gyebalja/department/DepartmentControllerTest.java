package com.gabia.gyebalja.department;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author : 이현재
 * Part : All
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.location=classpath:application-test.yml")
public class DepartmentControllerTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private Department department;

    @AfterEach
    public void cleanUp() {
        this.departmentRepository.deleteAll();
    }

    public DepartmentControllerTest(){
        // Interceptor 해제
        System.setProperty("spring.profiles.active.test", "true");

        // Department
        this.department = Department.builder()
                .name("테스트 - 부서")
                .depth(0)
                .parentDepartment(null)
                .build();
    }

    @Test
    @DisplayName("DepartmentController.getOneDepartment() 테스트 (단건 조회)")
    public void getOneDepartment(){
        // given
        Long saveId = departmentRepository.save(department).getId();
        String url = "http://localhost:" + port + "/api/v1/departments/" + saveId;

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.getForEntity(url, CommonJsonFormat.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
        assertThat(((LinkedHashMap) responseEntity.getBody().getResponse()).get("id")).isEqualTo(saveId.intValue());
    }

    @Test
    @DisplayName("DepartmentController.getAllDepartment() 테스트 (전체)")
    public void getAllDepartment(){
        // given
        int totalNumberOfData = 29;
        String name = "테스트 - 부서";
        for(int i = 0; i < totalNumberOfData; i++){
            departmentRepository.save(Department.builder().name(name).depth(0).parentDepartment(null).build());
        }

        String url = "http://localhost:" + port + "/api/v1/departments";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(headers);

        // when
        ResponseEntity<CommonJsonFormat> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, CommonJsonFormat.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getCode()).isEqualTo(StatusCode.OK.getCode());
        assertThat(responseEntity.getBody().getMessage()).isEqualTo(StatusCode.OK.getMessage());
    }
}
