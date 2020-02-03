package com.gabia.gyebalja.user;

import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.domain.UserGender;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class UserRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("User 저장 테스트(save)")
    public void saveTest() throws Exception {
        //given
        Department department = Department.builder()
                                .name("Team1")
                                .depth(2)
                                .parentDepartment(null)
                                .build();

        Department saveDepartment = departmentRepository.save(department);

        User user = User.builder()
                    .email("test@gabia.com")
                    .password("12345")
                    .name("User1")
                    .gender(UserGender.MALE)
                    .phone("000-0000-0000")
                    .tel("111-1111-1111")
                    .positionId(23L)
                    .positionName("인턴")
                    .department(saveDepartment)
                    .profileImg("src/img")
                    .build();

        //when
        User saveUser = userRepository.save(user);
        User findUser = userRepository.findById(saveUser.getId()).get();

        //then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(findUser).isEqualTo(user); //JPA 엔티티 동일성 보장하는지 검증

    }

    @Test
    @DisplayName("User 단건조회 테스트(findById)")
    public void findByIdTest() throws Exception {
        //given
        Department department = Department.builder()
                .name("Team1")
                .depth(2)
                .parentDepartment(null)
                .build();

        Department saveDepartment = departmentRepository.save(department);

        User user = User.builder()
                .email("test@gabia.com")
                .password("12345")
                .name("User1")
                .gender(UserGender.MALE)
                .phone("000-0000-0000")
                .tel("111-1111-1111")
                .positionId(23L)
                .positionName("인턴")
                .department(saveDepartment)
                .profileImg("src/img")
                .build();

        User saveUser = userRepository.save(user);

        /**
         * 영속성 컨텍스트를 초기화해줘야 findById를 할때 직접 select 쿼리가 날라감
         * 안해주면 그냥 존재하는 1차캐시에서 조회해오므로 디비에 select쿼리가 날라가지않음.
         */
        em.flush();
        em.clear();

        //when
        Optional<User> findUserById = userRepository.findById(saveUser.getId());

        //then
        assertThat(findUserById.get().getId()).isEqualTo(saveUser.getId());
        assertThat(findUserById.get().getName()).isEqualTo(saveUser.getName());
        assertThat(findUserById.get().getEmail()).isEqualTo(saveUser.getEmail());
        
    }

    @Test
    @DisplayName("User 전체 조회 테스트(findAll)")
    public void findAllTest() throws Exception {
        //given
        for (int i = 1; i <= 2; i++) {
            Department department = Department.builder()
                    .name("Team"+i)
                    .depth(2+i)
                    .parentDepartment(null)
                    .build();


            Department saveDepartment = departmentRepository.save(department);

            User user = User.builder()
                    .email("test@gabia.com")
                    .password("12345")
                    .name("User"+i)
                    .gender(UserGender.MALE)
                    .phone("000-0000-0000")
                    .tel("111-1111-1111")
                    .positionId(23L)
                    .positionName("인턴")
                    .department(saveDepartment)
                    .profileImg("src/img")
                    .build();

            User saveUser = userRepository.save(user);
        }

        em.flush();
        em.clear();

        //when
        List<User> userList = userRepository.findAll();
        //then
        assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("User 갯수 테스트(count)")
    public void countTest() throws Exception {
        //given
        for (int i = 1; i <= 3; i++) {
            Department department = Department.builder()
                    .name("Team"+i)
                    .depth(2+i)
                    .parentDepartment(null)
                    .build();


            Department saveDepartment = departmentRepository.save(department);

            User user = User.builder()
                    .email("test@gabia.com")
                    .password("12345")
                    .name("User"+i)
                    .gender(UserGender.MALE)
                    .phone("000-0000-0000")
                    .tel("111-1111-1111")
                    .positionId(23L)
                    .positionName("인턴")
                    .department(saveDepartment)
                    .profileImg("src/img")
                    .build();

            User saveUser = userRepository.save(user);
        }

        em.flush();
        em.clear();

        //when
        long count = userRepository.count();
        //then
        assertThat(count).isEqualTo(3);

    }

    @Test
    @DisplayName("User 삭제 테스트(delete)")
    public void deleteTest() throws Exception {
        //given
        Department department = Department.builder()
                .name("Team1")
                .depth(2)
                .parentDepartment(null)
                .build();

        Department saveDepartment = departmentRepository.save(department);

        User user = User.builder()
                .email("test@gabia.com")
                .password("12345")
                .name("User1")
                .gender(UserGender.MALE)
                .phone("000-0000-0000")
                .tel("111-1111-1111")
                .positionId(23L)
                .positionName("인턴")
                .department(saveDepartment)
                .profileImg("src/img")
                .build();

        User saveUser = userRepository.save(user);

        em.flush();
        em.clear();
        long beforeDeleteNum = userRepository.count();

        //when
        userRepository.delete(user);
        //then
        assertThat(userRepository.count()).isEqualTo(beforeDeleteNum-1);

    }

    @Test
    @DisplayName("User 비밀번호 변경 테스트(update)")
    public void updateTest() throws Exception {
        //given
        String updatePass = "1212";

        Department department = Department.builder()
                .name("Team1")
                .depth(2)
                .parentDepartment(null)
                .build();

        Department saveDepartment = departmentRepository.save(department);

        User user = User.builder()
                .email("test@gabia.com")
                .password("12345")
                .name("User1")
                .gender(UserGender.MALE)
                .phone("000-0000-0000")
                .tel("111-1111-1111")
                .positionId(23L)
                .positionName("인턴")
                .department(saveDepartment)
                .profileImg("src/img")
                .build();

        User saveUser = userRepository.save(user);

        //when
        saveUser.changePassword(updatePass);

        em.flush(); //영속성컨텍스트 초기화함으로써 더티체킹 -> Update 쿼리 발생
        em.clear();
        Optional<User> findUserById = userRepository.findById(user.getId());
        //then
        assertThat(findUserById.get().getId()).isEqualTo(saveUser.getId());
        assertThat(findUserById.get().getPassword()).isEqualTo(saveUser.getPassword());

    }

}
