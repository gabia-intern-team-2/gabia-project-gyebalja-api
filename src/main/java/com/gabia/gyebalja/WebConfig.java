package com.gabia.gyebalja;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    // .allowedOrigins("http://127.0.0.1:8282")
    // .allowedOrigins("http://localhost:8282")
    // .allowedMethods(
    //      HttpMethod.GET.name(),
    //      HttpMethod.HEAD.name(),
    //      HttpMethod.POST.name(),
    //      HttpMethod.PUT.name(),
    //      HttpMethod.DELETE.name());
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name());
    }
}