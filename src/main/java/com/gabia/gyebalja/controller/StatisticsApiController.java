package com.gabia.gyebalja.controller;

import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsApiController {
    /** 조회 - 메인 화면 */
    @GetMapping("/api/v1/statistics/main")
    public CommonJsonFormat getMainStatistics() {
        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), response);
    }
}
