package com.houzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();
        //允许跨域的域名，如果要携带cookie，不能写*，*代表所有域名都可以跨域访问
        config.addAllowedOrigin("http://localhost:9528");
        config.setAllowCredentials(true);//允许携带cookie
        config.addAllowedMethod("*");//代表所有的请求方法：GET POST PUT DELETE
        config.addAllowedHeader("*");//允许携带任何头信息
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",config);//过滤所有请求

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
