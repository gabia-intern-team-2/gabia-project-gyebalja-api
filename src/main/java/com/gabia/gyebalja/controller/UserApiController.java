package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.user.UserRequestDto;
import com.gabia.gyebalja.dto.user.UserResponseDto;
import com.gabia.gyebalja.service.UserService;
import com.gabia.gyebalja.service.jwt.JwtService;
import com.gabia.gyebalja.vo.GabiaUserInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Author : 정태균
 * Part : All
 */

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final JwtService jwtService;

    /** 로그인 후 가비아 프로필 요청 */
    @GetMapping("/api/v1/gabia-user")
    public CommonJsonFormat getGabiaProfile(HttpServletRequest request) throws Exception {
        GabiaUserInfoVo gabiaProfile = jwtService.getGabiaProfile(request);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), gabiaProfile);
    }

    /** 조회 - 사용자 정보 한 건 (토큰 사용)*/
    @GetMapping("/api/v1/users")
    public CommonJsonFormat getUserProfileDetail(HttpServletRequest request) throws Exception {
        UserResponseDto userProfileDetail = jwtService.getUserProfileDetail(request);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), userProfileDetail);
    }

    /** 조회 - 사용자 정보 한 건 */
    @GetMapping("/api/v1/users/{userId}")
    public CommonJsonFormat getOneUser(@PathVariable("userId") Long id) {
        UserResponseDto userResponseDto = userService.getOneUser(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), userResponseDto);
    }

    /** 저장 - 사용자 정보 한 건 */
    @PostMapping("/api/v1/users")
    public CommonJsonFormat postOneUser(@RequestBody UserRequestDto userRequestDto) {
        Long userId = userService.postOneUser(userRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(), userId);
    }

    /** 수정 - 사용자 정보 한 건 */
    @PutMapping("/api/v1/users/{userId}")
    public CommonJsonFormat putOneUser(@PathVariable("userId") Long id, @RequestBody UserRequestDto userRequestDto) {
        Long userId = userService.putOneUser(id, userRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(), userId);
    }
}
