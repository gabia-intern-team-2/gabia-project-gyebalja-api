package com.gabia.gyebalja.department;

import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.dto.department.DepartmentResponseDto;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.service.DepartmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author : 이현재
 * Part : All
 */

@Transactional
@SpringBootTest(properties = "spring.config.location=classpath:application-test.yml")
public class DepartmentServiceTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;

    @PersistenceContext
    private EntityManager em;

    private Department department;

    @Autowired
    public DepartmentServiceTest(){
        // Department
        this.department = Department.builder()
                .name("테스트 - 부서")
                .depth(0)
                .parentDepartment(null)
                .build();
    }

    @Test
    @DisplayName("DepartmentService.getOneDepartment() 테스트 (단건 조회)")
    public void getOneDepartmentTest(){
        // given
        Long saveId = departmentRepository.save(department).getId();
        em.flush();
        em.clear();

        // when
        DepartmentResponseDto departmentResponseDto = departmentService.getOneDepartment(saveId);

        // then
        assertThat(departmentResponseDto.getId()).isEqualTo(saveId);
    }

    @Test
    @DisplayName("DepartmentService.getAllDepartment() 테스트 (전체 조회)")
    public void getAllDepartmentTest() {
        // given
        int originalTotalNumberOfData = (int) departmentRepository.count();
        int targetIndex = originalTotalNumberOfData;
        int totalNumberOfData = 29;
        String name = "테스트 - 부서";
        for (int i = 0; i < totalNumberOfData; i++) {
            departmentRepository.save(Department.builder().name(name).depth(0).parentDepartment(null).build());
        }
        em.clear();
        em.flush();

        // when
        List<DepartmentResponseDto> departmentResponseDtos = departmentService.getAllDepartment();

        // then
        assertThat(departmentResponseDtos.size()).isEqualTo(totalNumberOfData);
        assertThat(departmentResponseDtos.get(targetIndex).getName()).isEqualTo(name);
    }
}
