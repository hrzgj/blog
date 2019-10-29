package com.blog.www.controller;

import com.blog.www.model.Result;
import com.blog.www.model.User;
import com.blog.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author cy
 */
@RestController
public class UserControl {

    @Autowired
    UserService userService;

    /**
     * @param user 用户
     * @return  注册是否成功
     */
    @GetMapping("/register")
    public String register(@RequestBody User user){
        if(user==null){
            return "0";
        }
        if(userService.accountAndMailExist(user)){
            return "账户或者邮箱已经存在";
        }

        return userService.insert(user);
    }

    /**
     * @param user 用户
     * @return 登录是否成功 "1" 成功 "0"失败
     */
    @GetMapping("/login")
    public Result login(@RequestBody User user){
//        if(user==null){
//            return "0";
//        }
        Result<User> result=new Result<>();
        result.setCode(200);
        result.setMsg("success");
        result.setData(user);
        return result;
//        return userService.findByAccountAndPassword(user);

    }

}
