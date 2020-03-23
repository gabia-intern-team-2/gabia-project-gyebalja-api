package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.user.UserRequestDto;
import com.gabia.gyebalja.dto.user.UserResponseDto;
import com.gabia.gyebalja.service.JwtService;
import com.gabia.gyebalja.service.UserService;
import com.gabia.gyebalja.vo.GabiaUserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "UserApiController V1")
@RestController
public class UserApiController {

    private final UserService userService;
    private final JwtService jwtService;

    /** 로그인 후 가비아 프로필 요청 */
    @ApiOperation(value = "getGabiaProfile : 가비아 프로필 요청", notes = "토큰을 복호화하여 가비아 고유 넘버, 이름, 이메일을 리턴")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/gabia-user")
    public CommonJsonFormat getGabiaProfile(HttpServletRequest request) throws Exception {
        GabiaUserInfoVo gabiaProfile = jwtService.getGabiaProfile(request);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), gabiaProfile);
    }

    /** 조회 - 사용자 정보 한 건 (토큰 사용)*/
    @ApiOperation(value = "getUserProfileDetail : 조회 - 사용자 정보 한 건", notes = "쿠키의 토큰을 사용하여 가비아 고유번호로 사용자정보 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/users")
    public CommonJsonFormat getUserProfileDetail(HttpServletRequest request) throws Exception {
        UserResponseDto userProfileDetail = jwtService.getUserProfileDetail(request);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), userProfileDetail);
    }

    /** 조회 - 사용자 정보 한 건 */
    @ApiOperation(value = "getOneUser : 조회 - 사용자 정보 한 건", notes = "사용자 Sequence 넘버로 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @GetMapping("/api/v1/users/{userId}")
    public CommonJsonFormat getOneUser(@PathVariable("userId") Long id) {
        UserResponseDto userResponseDto = userService.getOneUser(id);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), userResponseDto);
    }

    /** 저장 - 사용자 정보 한 건 */
    @ApiOperation(value = "postOneUser : 저장 - 사용자 정보 한 건", notes = "사용자 정보 저장 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PostMapping("/api/v1/users")
    public CommonJsonFormat postOneUser(@RequestBody UserRequestDto userRequestDto) {
        Long userId = userService.postOneUser(userRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(), userId);
    }

    /** 수정 - 사용자 정보 한 건 */
    @ApiOperation(value = "putOneUser : 수정 - 사용자 정보 한 건", notes = "사용자 정보 수정 요청")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST"),
            @ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR")
    })
    @PutMapping("/api/v1/users/{userId}")
    public CommonJsonFormat putOneUser(@PathVariable("userId") Long id, @RequestBody UserRequestDto userRequestDto) {
        Long userId = userService.putOneUser(id, userRequestDto);

        return new CommonJsonFormat(StatusCode.OK.getCode(),StatusCode.OK.getMessage(), userId);
    }
}
