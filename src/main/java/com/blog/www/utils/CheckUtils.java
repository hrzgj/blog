package com.blog.www.utils;

import com.blog.www.model.Collect;
import com.blog.www.model.Result;
import com.blog.www.model.ResultCode;
import com.blog.www.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyu
 * @date: 2019/11/16 11:36
 */
public class CheckUtils {
    public static boolean userSessionTimeOut(HttpServletRequest request,Result result){
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            result.setMsg("用户session失效");
            result.setCode(ResultCode.USER_SESSION_ERROR);
            return true;
        }else {
            return false;
        }
    }

    public static boolean userRightIsTrue(int id,int loginId,Result result){
        if(id!=loginId){
            result.setCode(ResultCode.RIGHT_ERROR);
            result.setMsg("用户权限错误");
            return true;
        }else {
            return false;
        }
    }

    public static boolean checkCollect(Collect collect,Result result){
        if(collect.getUserCollectId()==null||collect.getBlogId()==null){
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("传参为空");
            return true;
        }else {
            return false;
        }

    }
}
