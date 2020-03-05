//package com.gabia.gyebalja.service;
//
//import com.gabia.gyebalja.domain.Tag;
//import com.gabia.gyebalja.domain.User;
//import com.gabia.gyebalja.dto.tag.TagResponseDto;
//import com.gabia.gyebalja.dto.user.UserResponseDto;
//import com.gabia.gyebalja.exception.NotExistTagException;
//import com.gabia.gyebalja.exception.NotExistUserException;
//import com.gabia.gyebalja.repository.TagRepository;
//import com.gabia.gyebalja.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    /** 조회 - tag 한 건 */
//    public UserResponseDto getOneUser(Long id) {
//        Optional<User> findUser = userRepository.findById(id);
//
//        if(!findUser.isPresent())
//            throw new NotExistUserException("존재하지 않는 회원입니다.");
//
//        return TagResponseDto.builder()
//                .id(findTag.get().getId())
//                .name(findTag.get().getName())
//                .build();
//    }
//}
