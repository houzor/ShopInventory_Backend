package com.houzer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//扫mapper包
@MapperScan("com.houzer.mapper")
public class SpringbootInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootInventoryApplication.class, args);
    }


}
