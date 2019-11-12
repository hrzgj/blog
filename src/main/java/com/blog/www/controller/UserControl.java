package com.blog.www.controller;

import com.blog.www.model.Result;
import com.blog.www.model.ResultCode;
import com.blog.www.model.User;
import com.blog.www.service.UserService;
import com.blog.www.utils.CheckUtils;
import com.blog.www.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cy
 */
@RestController
public class UserControl {

    @Autowired
    private UserService userService;



    /**
     * 预注册
     * @param user 用户
     * @return  注册是否成功
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        Result<User> result=new Result<>();
        if(userService.insert(user)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("注册成功");
            return result;
        }else {
            result.setCode(ResultCode.MAIL_SEND_ERROR);
            result.setMsg("发送邮箱失败");
            return result;
        }


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
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("登录成功");
            result.setData(user);
            request.getSession().setAttribute("user", user);
            request.getSession().setMaxInactiveInterval(604800);
            return result;
        }else {
            result.setCode(ResultCode.PASSWORD_ERROR);
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
    public Result checkCode(@RequestParam("code")String code, HttpServletResponse response) throws IOException {
        Result<User> result=new Result<>();
        if(code==null){
            result.setMsg("修改了url的参数");
            result.setCode(ResultCode.REGISTER_ERROR2);
            response.sendRedirect("http://39.97.252.246:8080/login");
            return result;
        }
        if(userService.findCode(code)){
            result.setMsg("用户注册成功，已修改状态");
            result.setCode(ResultCode.SUCCESS);
            response.sendRedirect("http://39.97.252.246:8080/login");
            return result;
        }
        else {
            result.setCode(ResultCode.REGISTER_ERROR);
            result.setMsg("用户注册失败");
            response.sendRedirect("http://39.97.252.246:8080/login");
            return result;
        }


    }

    /**
     * 判断用户账户是否存在
     * @param user 用户账户
     * @return  result
     */
    @PostMapping("/checkAccount")
    public Result checkAccount(@RequestBody User user){
        Result result=new Result();
        if(userService.accountExit(user)){
            result.setMsg("账户存在");
            result.setCode(ResultCode.ACCOUNT_EXIT);
            return result;
        }else {
            result.setMsg("账户可用");
            result.setCode(ResultCode.SUCCESS);
            return result;
        }
    }

    /**
     * 判断用户邮箱是否存在
     * @param user 用户邮箱
     * @return  result
     */
    @PostMapping("/checkMail")
    public Result checkMail(@RequestBody User user){
        Result result=new Result();
        if(userService.mailExit(user)){
            result.setMsg("邮箱存在");
            result.setCode(ResultCode.MAIL_EXIT);
            return result;
        }else {
            result.setMsg("邮箱可用");
            result.setCode(ResultCode.SUCCESS);
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
        request.getSession().setMaxInactiveInterval(604800);
        if(CheckUtils.userSessionTimeOut(request,result)) {
            return result;
        }
        else
        {
            String oldPassword = MD5Utils.encode(oldPsw);
            String newPassword = MD5Utils.encode(newPsw);
            if (oldPassword.equals(user.getPassword())||oldPassword==user.getPassword()){
                boolean state = userService.updatePassword(user,newPassword);
                if (state){
                    result.setCode(ResultCode.SUCCESS);
                    result.setMsg("success");
                    result.setData(user);
                    user.setPassword(newPassword);
                }else {
                    result.setCode(ResultCode.UNSPECIFIED);
                    result.setMsg("修改密码失败");
                }
            }else {
                result.setMsg("旧密码不正确");
                result.setCode(ResultCode.PASSWORD_ERROR);
            }
            request.getSession().setAttribute("user", user);
            return result;
        }
    }

    /**
     * 忘记密码时发送随机验证码
     * @param mail 邮箱
     * @return 发送成功与否
     */
    @GetMapping("/sendCode")
    public  Result sendRandomCode(@RequestParam("mail") String mail){
        Result<User> result = new Result<>();
        if(userService.sendRandomCode(mail)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("发送随机验证码成功");
        }else {
            result.setCode(ResultCode.UNSPECIFIED);
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
    public  Result forgetPassword(@RequestParam("mail")String mail,@RequestParam("password") String newPassword,@RequestParam("randomcode") String code){
        Result<User> result = new Result<>();
        User user = userService.findUserByMail(mail);
        //从数据库中找到此验证码的对象id，如一致则可以修改密码
        if(userService.findCodeInForget(user,code)){
            if (userService.forgetPassword(user,newPassword)){
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("修改密码成功");
                result.setData(user);
            }else{
                result.setCode(ResultCode.UNSPECIFIED);
                result.setMsg("修改密码失败");
            }
        }else{
            result.setCode(ResultCode.IDENTITY_ERROR);
            result.setMsg("验证码错误");
        }
        return result;
    }


}
