package com.blog.www.controller;

import com.blog.www.model.Result;
import com.blog.www.model.User;
import com.blog.www.service.UserService;
import com.blog.www.utils.MD5Utils;
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
    public Result register(@RequestBody User user){
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
     * @return 登录成功
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletRequest request){
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

    @PostMapping("/checkAccount")
    public Result checkAccount(@RequestBody User user){
        Result result=new Result();
        if(userService.accountExit(user)){
            result.setMsg("账户存在");
            result.setCode(200);
            return result;
        }else {
            result.setMsg("账户可用");
            result.setCode(200);
            return result;
        }
    }

    @PostMapping("/checkMail")
    public Result checkMail(@RequestBody User user){
        Result result=new Result();
        if(userService.mailExit(user)){
            result.setMsg("邮箱存在");
            result.setCode(200);
            return result;
        }else {
            result.setMsg("邮箱可用");
            result.setCode(200);
            return result;
        }
    }

    /**
     * 修改密码
     * @param oldPsw  旧密码
     * @param newPsw   新密码
     * @return 修改成功与否
     */
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestParam("oldpassword") String oldPsw, @RequestParam("newpassword") String newPsw, HttpServletRequest request) {
        Result<User> result=new Result<>();
        User user = (User) request.getSession().getAttribute("user");
        if(user==null) {
            result.setMsg("用户为空");
            result.setCode(404);
            return  result;
        }else{
            String oldPassword = MD5Utils.encode(oldPsw);
            String newPassword = MD5Utils.encode(newPsw);


            if (oldPassword.equals(user.getPassword())||oldPassword==user.getPassword()){
                boolean state = userService.updatePassword(user,newPassword);
                if (state){
                    result.setCode(200);
                    result.setMsg("success");
                    result.setData(user);
                    user.setPassword(newPassword);
                }else {
                    result.setCode(200);
                    result.setMsg("修改密码失败");
                }
            }else {
                result.setMsg("旧密码不正确");
                result.setCode(404);
            }
            request.getSession().setAttribute("user", user);
            return result;
        }
    }

    /**
     * 忘记密码时发送随机验证码
     * @return 发送成功与否
     */
    @GetMapping("/sendCode")
    public  Result sendRandomCode(HttpServletRequest request){
        Result<User> result = new Result<>();
        User user = (User) request.getSession().getAttribute("user");
        if(userService.sendRandomCode(user)){
            result.setCode(200);
            result.setMsg("发送随机验证码成功");
        }else {
            result.setCode(200);
            result.setMsg("发送随机验证码失败");
        }
        return result;
    }

    /**
     * 忘记密码
     * @param newPassword 新的密码
     * @param code 随机验证码
     * @return 成功与否
     */
    @PostMapping("/forgetPassword")
    public  Result forgetPassword(@RequestParam("password") String newPassword,@RequestParam("randomcode") String code,HttpServletRequest request){
        Result<User> result = new Result<>();
        User user = (User) request.getSession().getAttribute("user");
        //从数据库中找到此验证码的对象id，如一致则可以修改密码
        if(userService.findCodeInForget(user,code)){
            if (userService.forgetPassword(user,newPassword)){
                result.setCode(200);
                result.setMsg("修改密码成功");
                result.setData(user);
            }else{
                result.setCode(200);
                result.setMsg("修改密码失败");
            }
        }else{
            result.setCode(200);
            result.setMsg("验证码错误");
        }
        return result;
    }


}
