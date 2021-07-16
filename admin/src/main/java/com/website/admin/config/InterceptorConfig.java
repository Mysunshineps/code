package com.website.admin.config;

import com.website.admin.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/admin/user/**","/admin/webSocket/message/**","/index.html/**","/swagger/docs/**","/swagger-ui.html/**","/swagger-resources/**","/v2/**");
                .excludePathPatterns("/admin/user/**","/admin/webSocket/message/**");

    }
}
