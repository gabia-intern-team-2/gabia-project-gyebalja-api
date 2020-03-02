package com.gabia.gyebalja.controller;


import com.gabia.gyebalja.common.CommonJsonFormat;
import com.gabia.gyebalja.common.StatusCode;
import com.gabia.gyebalja.oauth.bo.GabiaLoginApi;
import com.gabia.gyebalja.oauth.bo.GabiaLoginBo;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor  //생성자 주입방식을 사용하기 위해 사용
@RestController
public class GabiaLoginController {

    GabiaLoginBo gabiaLoginBo = new GabiaLoginBo();

    /** 로그인 요청 */
    @GetMapping("/api/v1/login")
    public CommonJsonFormat getLoginPage() {
        String clientId = "vtovgjlvdskcasrbiownu1wgjy2xures5e3276b98dd6a9.36873902.open.apps";
        String redirectUrl = "https://api.hiworks.com/open/auth/authform?client_id="+ clientId + "&access_type=online";
        return new CommonJsonFormat(StatusCode.OK.getCode(), StatusCode.OK.getMessage(), redirectUrl);

    }

    /** 하이웍스 로그인 콜백 */
    @GetMapping("/auth/callback")
    public void getAuthCode(@RequestParam("auth_code") String authCode) {
        System.out.println("authCode = " + authCode);
        String url = "https://api.hiworks.com/open/auth/accesstoken";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("client_id", "vtovgjlvdskcasrbiownu1wgjy2xures5e3276b98dd6a9.36873902.open.apps");
        params.add("client_secret", "trn0nxapmfrxjrkgharx030kfymiyjew");
        params.add("grant_type", "authorization_code");
        params.add("auth_code", authCode);
        params.add("access_type", "online");

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<String>(params.toString(), headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LinkedHashMap> response = restTemplate.postForEntity(url, params, LinkedHashMap.class);
        LinkedHashMap lm = (LinkedHashMap) response.getBody().get("data");
        System.out.println("response.getBody() = " + response.getBody());
        System.out.println("response = " + lm.get("access_token"));
        String accessToken;
        accessToken = lm.get("access_token").toString();

        if( accessToken != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization","Bearer " + accessToken);
            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<LinkedHashMap> response2 = restTemplate.exchange("https://api.hiworks.com/user/v2/me", HttpMethod.GET, entity,LinkedHashMap.class);
            System.out.println("response2.getBody() = " + response2.getBody());
        }
    }



}
