package com.houzer.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.houzer.common.vo.Result;
import com.houzer.entity.User;
import com.houzer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-04-30
 */
@RestController
@RequestMapping("/user")
//解决跨域问题不能直接用注解，要用配置类，因为后面写了拦截器去拦截请求判断token
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user) {
        Map<String,Object> data=userService.login(user);
        if(data!=null){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或密码错误");
    }
    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@RequestParam String token) {
        Map<String,Object> data=userService.getUserInfo(token);
        if(data!=null) {
            return Result.success(data);
        }
        return Result.fail(20003,"获取用户信息失败");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token) {
        //给了个成功的结果就下线了，这里应该要还要记录一下打卡
        userService.logout(token);
        return Result.success();
    }

//    @GetMapping("/all")
//    public Result<List<User>> getAllUser() {
//        List<User> list = userService.list();
//        //这里要加个判断，如果list为空，就返回失败
//        return Result.success(list, "查询成功");
//    }
    @GetMapping("/judge")
    public Result<User> getAllUser(@RequestParam String username) {
        User data = userService.getByUserName(username);
        //这里要加个判断，如果list为空，就返回失败
        return Result.success(data);
    }


    //有可能查询的条件为空，require=false解决,这里的分页一定会传的，因为前端有默认值，默认值size为10，page为1
    @GetMapping("/list")
    public Result<Map<String,Object>> getUserByPage(@RequestParam(value = "username",required = false) String username,
                                                    @RequestParam(value = "phone",required = false) String phone,
                                                    @RequestParam(value = "pageNo") Long pageNo,
                                                    @RequestParam(value = "pageSize") Long pageSize) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        //拼接条件，里面需要判断传的用户名是否为空
        wrapper.like(username!=null&&!username.isEmpty(),User::getUsername,username);
        wrapper.like(phone!=null&&!phone.isEmpty(),User::getPhone,phone);
        //mybatisplus的分页功能
        Page<User> page = new Page<>(pageNo,pageSize);
        //mybatis自带的分页查询，只需要传入page和wrapper即可，返回的是page对象,这里需要配置mybatis分页的拦截器，别忘了
        userService.page(page,wrapper);
        //返回数据
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
//        得到所有的记录
        data.put("rows",page.getRecords());
        return Result.success(data);
    }

    //add返回为空就行，不需要返回数据
    @PostMapping
    public Result<?> addUser(@RequestBody User user) {
        //这里涉及一个密码，不可能以明文的形式，要做加密处理，本来用MD5处理，springsicurity有个BCryptPasswordEncoder，可以做加密处理
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        return Result.success("新增用户成功！");
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user) {
        System.out.println(user);
        //这里要注意，MP如果传入的字段值为空，字段值不更新，而不是更新为null,这里可以判断是修改用户还是进入修改个人了，我就没做另外的接口
        if(user.getPassword()!=null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.updateById(user);
        return Result.success("修改用户成功！！");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user=userService.getById(id);
        return Result.success(user);
    }
    @DeleteMapping("/{id}")
    public Result<?> deleteUserById(@PathVariable("id") Integer id){
        //这里涉及逻辑删除，并不是真的删除数据库里面的,但是我没做
        userService.removeById(id);
        return Result.success("删除用户成功！！");
    }
}

