package com.houzer.config;

import com.houzer.interceptor.JwTInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercetorConfig implements WebMvcConfigurer {
    @Autowired
    private JwTInterceptor jwTInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(jwTInterceptor);
////        拦截所有但是不能拦截登录接口
        interceptorRegistration.addPathPatterns("/**")
//                排除多个
                .excludePathPatterns("/user/login",
                        "/user/info",
                        "/user/logout",
                        "/error",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/**");
    }
}
