package com.blog.www.controller;

import com.blog.www.model.Result;
import com.blog.www.model.User;
import com.blog.www.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cy
 */
@RestController
public class UserControl {

    @Autowired
    UserService userService;



    /**
     * 预注册
     * @param user 用户
     * @return  注册是否成功
     */
    @PostMapping("/register")
    public Result register( User user){
        Result<User> result=new Result<>();
        if(userService.accountAndMailExist(user)){
            result.setMsg("账户或者邮箱已经存在");
            result.setCode(404);
            return result;
        }
        userService.insert(user);
        result.setCode(200);
        result.setMsg("注册成功");
        return result;

    }

    /**
     * 登录
     * @param user 用户
     * @return 登录是否成功 "1" 成功 "0"失败
     */
    @PostMapping("/login")
    public Result login( User user, HttpServletRequest request){
        if(user==null){
            return null;
        }
        Result<User> result=new Result<>();
        user=userService.findByAccountAndPassword(user);
        if(user!=null) {
            result.setCode(200);
            result.setMsg("success");
            result.setData(user);
            request.getSession().setAttribute("user", user);
            return result;
        }else {
            result.setCode(200);
            result.setMsg("账户密码错误");
            return result;
        }
    }

    /**
     * 用户点击邮件链接，成功注册
     * @param code 验证码
     * @return 注册
     */
    @GetMapping("/checkCode")
    public Result checkCode(@Param("code")String code){
        Result<User> result=new Result<>();
        if(code==null){
            result.setMsg("修改了url的参数");
            result.setCode(404);
            return result;
        }
        if(userService.findCode(code)){
            result.setMsg("用户注册成功，已修改状态");
            result.setCode(200);
            return result;
        }
        else {
            result.setCode(404);
            result.setMsg("用户注册失败");
            return result;
        }


    }

}
