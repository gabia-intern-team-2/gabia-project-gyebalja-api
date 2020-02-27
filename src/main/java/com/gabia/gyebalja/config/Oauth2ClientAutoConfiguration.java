package com.gabia.gyebalja.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;

/**
 *  [설명 1]
 *  Oauth2 Client 에 대한 설정을 자동으로 해주는 클래스
 *
 *  OAuth2ClientRegistrationRepositoryConfiguration.class
 *  OAuth2WebSecurityConfiguration.class
 * */

@Configuration
@AutoConfigureBefore(SecurityAutoConfiguration.class)
@ConditionalOnClass({EnableWebSecurity.class, ClientRegistration.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Import({OAuth2ClientRegistrationRepositoryConfiguration.class, OAuth2WebSecurityConfiguration.class})
public class Oauth2ClientAutoConfiguration {
}
