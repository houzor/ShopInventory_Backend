package com.houzer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.houzer.entity.User;
import com.houzer.mapper.UserMapper;
import com.houzer.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.houzer.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houzer
 * @since 2023-05-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public Map<String, Object> login(User user) {
//        先查用户名，再查密码
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User loginUser=this.baseMapper.selectOne(wrapper);
        //如果不为空，对比密码
        if(loginUser!=null&& passwordEncoder.matches(user.getPassword(),loginUser.getPassword())){
            //将密码设为空再存token里
            loginUser.setPassword(null);
            //创建jwt
            String token=jwtUtil.createToken(loginUser);
            //返回数据
            Map<String,Object> data=new HashMap<>();
            data.put("token",token);
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        //根据他的token解出来username和avatar再返回给前端
        User loginUser=null;
        try{
            loginUser=jwtUtil.parseToken(token, User.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(loginUser!=null){
            Map<String,Object> data=new HashMap<>();
            List<String> rolelist=new ArrayList<>();
            data.put("username",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());
            if(loginUser.getRole()==1)
            {
                rolelist.add("admin");
            } else if (loginUser.getRole()==0) {
                rolelist.add("user");
            }
            data.put("roles",rolelist);
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        //这里应该要记录一下打卡

    }

    @Override
    public User getByUserName(String username) {
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,username);
        User loginUser=this.baseMapper.selectOne(wrapper);
        if(loginUser!=null)
        {
            loginUser.setPassword(null);
            return loginUser;
        }
        return null;
    }
}
