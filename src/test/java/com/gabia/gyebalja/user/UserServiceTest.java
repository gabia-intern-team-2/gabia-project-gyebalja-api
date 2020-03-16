package com.gabia.gyebalja.user;

import com.gabia.gyebalja.domain.Department;
import com.gabia.gyebalja.domain.GenderType;
import com.gabia.gyebalja.domain.User;
import com.gabia.gyebalja.dto.user.UserRequestDto;
import com.gabia.gyebalja.dto.user.UserResponseDto;
import com.gabia.gyebalja.repository.DepartmentRepository;
import com.gabia.gyebalja.repository.UserRepository;
import com.gabia.gyebalja.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author : 정태균
 * Part : All
 */

@Transactional
@SpringBootTest(properties = "spring.config.location=classpath:application-test.yml")
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired private DepartmentRepository departmentRepository;

    @Autowired
    private UserService userService;

    @PersistenceContext
    EntityManager em;

    private Department department;
    private User user;

    @BeforeEach
    public void setUp(){
        departmentRepository.save(this.department);
        userRepository.save(this.user);
    }

    @Autowired
    public UserServiceTest() {
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
                .gender(GenderType.MALE)
                .phone("010-2345-5678")
                .tel("02-2345-5678")
                .positionId(5L)
                .positionName("직원")
                .department(this.department)
                .profileImg(null)
                .build();
    }

    @Test
    @DisplayName("userService.postOneUser() 테스트 (단건 저장)")
    public void postOneUser() throws Exception {
        //given
        UserRequestDto userRequestDto = new UserRequestDto(this.user);

        //when
        Long userId = userService.postOneUser(userRequestDto);
        em.clear();
        User findUser = userRepository.findById(userId).get();

        //then
        assertThat(userId).isEqualTo(findUser.getId());
        assertThat(userRequestDto.getName()).isEqualTo(findUser.getName());
    }

    @Test
    @DisplayName("userService.getOneUser() 테스트 (단건 조회)")
    public void getOneUser() throws Exception {
        //given
        UserRequestDto userRequestDto = new UserRequestDto(this.user);
        Long userId = userService.postOneUser(userRequestDto);
        em.clear();

        //when
        UserResponseDto findUser = userService.getOneUser(userId);

        //then
        assertThat(userId).isEqualTo(findUser.getId());
        assertThat(userRequestDto.getName()).isEqualTo(findUser.getName());
    }

    @Test
    @DisplayName("userService.putOneUser() 테스트 (단건 수정)")
    public void putOneUser() throws Exception {
        //given
        String updateEngName = "updateEngName";
        UserRequestDto userRequestDto = new UserRequestDto(this.user);
        Long savedId = userService.postOneUser(userRequestDto);

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

        Long updateId = userService.putOneUser(savedId, updateRequestDto);
        UserResponseDto findUser = userService.getOneUser(updateId);

        //then
        assertThat(savedId).isEqualTo(updateId);
        assertThat(updateEngName).isEqualTo(findUser.getEngName());
    }
}
