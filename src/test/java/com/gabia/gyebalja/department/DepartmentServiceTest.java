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

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
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
    public void findTest(){
        // given
        Long saveId = departmentRepository.save(department).getId();
        em.flush();
        em.clear();

        // when
        DepartmentResponseDto departmentResponseDto = departmentService.getOneDepartment(saveId);

        // then
        assertThat(departmentResponseDto.getId()).isEqualTo(saveId);
    }
}
