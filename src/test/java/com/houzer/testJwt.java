package com.houzer;


import com.houzer.entity.User;
import com.houzer.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testJwt {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testCreatJwt(){
        User user = new User();
        user.setUsername("admin");
//        user.setPassword("123456");
        user.setPhone("123456789");
        String token = jwtUtil.createToken(user);
        System.out.println(token);
    }
    @Test
    public void jiexitoken(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjZmE0NTA1Ni1jMjFiLTQ3MjctYmMwNS04ZjY5ZGRmMjM5MTQiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTIzNDU2Nzg5XCIsXCJ1c2VybmFtZVwiOlwiemhhbmdzYW5cIn0iLCJpc3MiOiJzeXN0ZW0iLCJpYXQiOjE2ODI4ODE4NzAsImV4cCI6MTY4Mjg4MzY3MH0.aAyIk2STm-MqkogwW45gI2IfMVmPUgC2n4T9FOZN2s4";
        Claims claims=jwtUtil.parseToken(token);
        System.out.println(claims);
    }
    @Test
    public void jiexitoken2(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmMzE3ZDM1OC1iOGIyLTQ3ODAtYWJiZi0xMTU2YWYzYjdmMDYiLCJzdWIiOiJ7XCJhdmF0YXJcIjpcInd1XCIsXCJkZWxldGVkXCI6MCxcImVtYWlsXCI6XCIyMjY5NzY4MjM1QHFxLmNvbVwiLFwiaWRcIjoxLFwicGhvbmVcIjpcIjEzNDMxODkyMTM4XCIsXCJzdGF0dXNcIjoxLFwidXNlcm5hbWVcIjpcImFkbWluXCJ9IiwiaXNzIjoic3lzdGVtIiwiaWF0IjoxNjgyOTE5MzEyLCJleHAiOjE2ODI5MjExMTJ9.pj8i94znYWoJXy7W0DAOH0q8JFIvPQTwh_oUncYXqhs";
        User user=jwtUtil.parseToken(token,User.class);
//        快速得到用户信息
        System.out.println(user);
    }
}
