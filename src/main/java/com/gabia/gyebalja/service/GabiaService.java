package com.gabia.gyebalja.service;

import com.gabia.gyebalja.vo.GabiaTokenVo;
import com.gabia.gyebalja.vo.GabiaUserInfoVo;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class GabiaService {

    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    @Value("${spring.social.gabia.client_id}")
    private String gabiaClientId;

    @Value("${spring.social.gabia.client_secret}")
    private String gabiaClientSecret;
    // Access Token 요청
    public GabiaTokenVo getAccessToken(String authCode) {
        // Set Header : Content-type application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // Set Body params
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", gabiaClientId);
        params.add("client_secret", gabiaClientSecret);
        params.add("grant_type", "authorization_code");
        params.add("auth_code", authCode);
        params.add("access_type", "offline");
        // Set Http Entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<HashMap> response = restTemplate.postForEntity(env.getProperty("spring.social.gabia.url.token"), request, HashMap.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody().get("data").toString(), GabiaTokenVo.class);
        }
        return null;
    }
    // 내 정보 조회 요청
    public GabiaUserInfoVo getGabiaProfile(String accessToken) {
        // Set Header : Content-type application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        // Set Http Entity
        HttpEntity<String> request = new HttpEntity<String>(headers);
        try {
            // Request profile
            ResponseEntity<HashMap> response = restTemplate.exchange(env.getProperty("spring.social.gabia.url.profile"),HttpMethod.GET, request, HashMap.class);
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody().toString(), GabiaUserInfoVo.class);
        } catch (Exception e) {
            // 추후 추가 예정
        }
        return null;
    }

}
