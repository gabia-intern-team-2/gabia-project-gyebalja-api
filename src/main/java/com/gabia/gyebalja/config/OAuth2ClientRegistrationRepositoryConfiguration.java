package com.gabia.gyebalja.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.ClientsConfiguredCondition;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesRegistrationAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.ArrayList;
import java.util.List;

/**
 *  [설명 2]
 *  Registration Repository 에 잘 알려진 OAuth2 Provider 들에 대한 각각의 Client Registration 설정을 해주는 역할 (?)
 *
 *  clientRegistrationRepository() 메소드에 ConditionalOnMissingBean() 를 해준 이유
 *   - clientRegistrationRepository Bean을 직접 설정 파일에 생성, 주입 하는 경우에는 Spring Boot 에서 자동으로 설정해주는 값을 이용할 수 없음 (아마도 GOOGLE, FACEBOOK (?))
 *   - Spring Boot Security Oauth2 Client 에서 제공하지 않는 다른 Provider(KAKAO, GABIA ...) 를 이용하면서 동시에 GOOGLE, FACEBOOK 등을 사용하고 시다면 설정
 * */
@Configuration
@EnableConfigurationProperties(OAuth2ClientProperties.class)
@Conditional(ClientsConfiguredCondition.class)
public class OAuth2ClientRegistrationRepositoryConfiguration {

    private final OAuth2ClientProperties properties;

    OAuth2ClientRegistrationRepositoryConfiguration(OAuth2ClientProperties properties){
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(ClientRegistrationRepository.class)
    public InMemoryClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = new ArrayList<>(OAuth2ClientPropertiesRegistrationAdapter.getClientRegistrations(this.properties).values());
        return new InMemoryClientRegistrationRepository(registrations);
    }
}
