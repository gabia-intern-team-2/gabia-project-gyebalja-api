package com.gabia.gyebalja.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

/**
 *  [설명 5]
 *  Security + OAuth2 설정
 *  Spring Security 관련 설정, 우리가 사용할 OAuth2 제공자 설정
 * */
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();
//        http.authorizeRequests()
//                    .antMatchers("/", "/oauth2/**", "/login/**").permitAll()
//                    .anyRequest().authenticated()
//                .and()
//                    .oauth2Login()
//                    .defaultSuccessUrl("/")
//                    .failureUrl("/login")
//                .and()
//                    .headers().frameOptions().disable()
//                .and()
//                    .exceptionHandling()
//                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(("/login")))
//                .and()
//                    .formLogin()
//                    .successForwardUrl("/board/list")
//                .and()
//                    .logout()
//                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/")
//                    .deleteCookies("JSESSIONID")
//                    .invalidateHttpSession(true)
//                .and()
//                    .addFilterBefore(filter, CsrfFilter.class).csrf().disable();
        http.authorizeRequests()
                    .antMatchers("/login", "/oauth2/**", "/", "/auth/callback")
                    .permitAll()
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/")
                    .failureUrl("/login");
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            OAuth2ClientProperties oAuth2ClientProperties,
            @Value("${custom.oauth2.gabia.clientId}") String gabiaClientId,
            @Value("${custom.oauth2.gabia.clientSecret}") String gabiaClientSecret) {
        // List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream().map(client -> getRegistration(oAuth2ClientProperties, client)).filter(Objects::nonNull).collect(Collectors.toList());
        // registrations.add(CustomOAuth2Provider.GABIA.getBuilder("gabia").clientId(gabiaClientId).clientSecret(gabiaClientSecret).jwkSetUri("temp").build());
        List<ClientRegistration> registrations = new ArrayList<>();
        registrations.add(CustomOAuth2Provider.GABIA.getBuilder("gabia").clientId(gabiaClientId).clientSecret(gabiaClientSecret).jwkSetUri("temp").build());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
        return null;
    }
}
