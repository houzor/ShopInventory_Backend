package com.houzer.interceptor;

import com.alibaba.fastjson2.JSON;
import com.houzer.common.vo.Result;
import com.houzer.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JwTInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("X-Token");
        System.out.println(token);
        if(token!=null){
            try {
                //有问题就会抛异常，这里捕捉一下
                jwtUtil.parseToken(token);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //拦截,并且返回json信息
        response.setContentType("application/json;charset=utf-8");
        Result<Object> fail = Result.fail(20003, "jwt无效，请重新登陆");
        //这里要转成字符串
        response.getWriter().write(JSON.toJSONString(fail));
        return false;
    }
}
