package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.CookieBox;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.dto.user.UserResponseDto;

import com.gabia.gyebalja.service.jwt.JwtService;
import com.gabia.gyebalja.vo.GabiaUserInfoVo;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    //private final UserService userService;
    private final JwtService jwtService;
    private final Gson gson;

    /** 로그인 후 가비아 프로필 요청 */
    @GetMapping("/api/v1/gabia-user")
    public CommonJsonFormat getGabiaProfile(HttpServletRequest request) throws Exception {
        GabiaUserInfoVo gabiaProfile = jwtService.getGabiaProfile(request);

        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), gabiaProfile);
    }

//    /** 조회 - 사용자 정보 한 건 */
//    @GetMapping("/api/v1/users/{userId}")
//    public CommonJsonFormat getOneUser(@PathVariable("userId") Long id) {
//        UserResponseDto userResponseDto = userService.getOneUser(id);
//
//        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), UserResponseDto);
//    }
//
//    /** 수정 - tag 한 건 (태그 단건 수정) */
//    @PutMapping("/api/v1/tags/{tagId}")
//    public CommonJsonFormat putOneTag(@PathVariable("tagId") Long id, @RequestBody TagRequestDto tagRequestDto) {
//        Long tagId = tagService.putOneTag(id, tagRequestDto);
//
//        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), tagId);
//    }

}
