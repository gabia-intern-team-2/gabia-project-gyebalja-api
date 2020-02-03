package com.gabia.gyebalja.department;

import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class DepartmentRepositoryTest {
    @PersistenceContext
    EntityManager em;

    private final DepartmentRepository departmentRepository;

    private Department department;

    @Autowired
    public DepartmentRepositoryTest(DepartmentRepository departmentRepository){
        // Repository
        this.departmentRepository = departmentRepository;

        // Department
        this.department = Department.builder().name("테스트 - 팀").depth(0).parentDepartment(null).build();
    }

    /** Create Test */
    @Test
    public void saveTest(){
        // given
        Department department = this.department;

        // when
        Department saveDepartment = departmentRepository.save(department);
        em.flush();
        em.clear();

        // then
        Department findDepartment = departmentRepository.findById(saveDepartment.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));
        assertThat(findDepartment.getName()).isEqualTo(saveDepartment.getName());
        assertThat(findDepartment.getParentDepartment()).isEqualTo(saveDepartment.getParentDepartment());
    }

    /** Retrieve Test */
    @Test
    public void findTest(){
        /**
         * [의문]
         * 새 데이터 삽입하고 삽입한 값을 반환하여 값을 비교하는 테스트
         * --> 이 경우 saveTest() 와 유사
         * --> 사실 saveTest()에서 이미 조회 함수를 사용하는데 이게 삽입만을 위한 테스트인지 의문이 듬
         * --> saveTest()에서 다른 테스트 방법을 생각해봐도 좋을 것 같음
         * */

        // given
        Department department = this.department;
        Department saveDepartment = departmentRepository.save(department);
        em.flush();
        em.clear();

        // when
        Department findDepartment = departmentRepository.findById(saveDepartment.getId()).orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다."));

        // then
        assertThat(findDepartment.getName()).isEqualTo(saveDepartment.getName());
        assertThat(findDepartment.getParentDepartment()).isEqualTo(saveDepartment.getParentDepartment());
    }

    /** Update Test */
    @Test
    public void updateTest(){
        // given
        String updateName = "테스트팀업데이트";
        int updateDepth = 100;
        Department department = this.department;
        Department saveDepartment = departmentRepository.save(department);
        Department findDepartment = departmentRepository.findById(saveDepartment.getId()).orElseThrow(() -> new IllegalArgumentException(("해당 데이터가 없습니다.")));
        findDepartment.changeName(updateName);
        findDepartment.changeDepth(updateDepth);
        em.flush();
        em.clear();

        // when
        Department updateDepartment = departmentRepository.save(findDepartment);

        // then
        assertThat(updateDepartment.getName()).isEqualTo(updateName);
        assertThat(updateDepartment.getDepth()).isEqualTo(updateDepth);
    }

    /** Delete Test */
    @Test
    public void deleteTest(){
        // given
        Long totalNumberOfData = departmentRepository.count();
        Department department = this.department;
        Department saveDepartment = departmentRepository.save(department);
        em.flush();
        em.clear();

        // when
        departmentRepository.deleteById(saveDepartment.getId());

        // then
        assertThat(departmentRepository.count()).isEqualTo(totalNumberOfData);
        assertThat(departmentRepository.findById(saveDepartment.getId())).isEqualTo(Optional.empty());
    }
}
