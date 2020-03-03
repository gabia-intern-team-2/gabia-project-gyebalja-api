//package com.gabia.gyebalja.config.auth;
//
//import lombok.RequiredArgsConstructor;
//import lombok.Value;
//import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository (
//            OAuth2ClientProperties oAuth2ClientProperties,
//            @Value("${custom.oauth2.gabia.client-id}") String gabiaClientId,
//            @Value("${custom.oauth2.gabia.client-secret}") String gabiaClientSecret) {
//
//    }
//    )
//}
