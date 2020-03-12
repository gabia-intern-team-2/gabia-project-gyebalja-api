package com.gabia.gyebalja.vo;

import lombok.Data;

/**
 * Author : 정태균
 * Part : All
 */

@Data
public class GabiaTokenVo {
    private String access_token;
    private String refresh_token;
    private long office_no;
    private long user_no;
}
