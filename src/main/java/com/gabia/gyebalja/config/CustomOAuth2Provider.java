package com.gabia.gyebalja.config;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/**
 *  [설명 4]
 *  CommonAuth2Provider(잘 알려진 Oauth2 제공자 GOOGLE, FACEBOOK ... 를 조금 더 쉽게 설정하기 위해 제공되는 클래스)를 본따 만든 클래스
 *  기본적으로 OAuth2 Client 에 필요한 정보들(redirect uri, authorization uri, token uri, userInfo uri)이 추가되어 있는 Builder 반환
 * */
public enum CustomOAuth2Provider {
    GABIA {
        @Override
        public ClientRegistration.Builder getBuilder(String registrationId){
            ClientRegistration.Builder builder = getBuilder(registrationId, ClientAuthenticationMethod.POST, DEFAULT_LOGIN_REDIRECT_URL)
                    .authorizationUri("https://api.hiworks.com/open/auth/authform?access_type=offline")
                    .tokenUri("https://api.hiworks.com/open/auth/accesstoken")
                    .userInfoUri("https://api.hiworks.com/user/v2/me")
                    .userNameAttributeName("id")
                    .clientName("gabia");

            return builder;
        }
    };

    private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login";

    protected final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method, String redirectUri){
        ClientRegistration.Builder builder = ClientRegistration
                .withRegistrationId(registrationId)
                .clientAuthenticationMethod(method)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUriTemplate(redirectUri);

        return builder;
    }

    public abstract ClientRegistration.Builder getBuilder(String registrationId);
}
// access_type
// https://api.hiworks.com/open/auth/authform?response_type=code&client_id=vtovgjlvdskcasrbiownu1wgjy2xures5e3276b98dd6a9.36873902.open.apps&state=OAsYUpko9uBhG7CcT1nUvlfBAdsluPN5MYukl2rWUl0%3D&redirect_uri=http://localhost:8282/login/oauth2/code/gabia
// https://api.hiworks.com/open/auth/authform?response_type=code&client_id=vtovgjlvdskcasrbiownu1wgjy2xures5e3276b98dd6a9.36873902.open.apps&state=I8pqeU1IG_UL_jJ9zXruNoyPq6G3X5IesH6sDfC97Lo%3D&redirect_uri=http://localhost:8282/auth/callback
// https://api.hiworks.com/open/auth/authform?response_type=code&client_id=vtovgjlvdskcasrbiownu1wgjy2xures5e3276b98dd6a9.36873902.open.apps&state=9KXblSd50o2askQd33te_dAU1H9ALyuhXBZIlJ6LsVM%3D&redirect_uri=http://localhost:8080/auth/callback