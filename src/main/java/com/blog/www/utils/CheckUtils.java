package com.blog.www.utils;

import com.blog.www.model.Result;
import com.blog.www.model.ResultCode;
import com.blog.www.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyu
 * @date: 2019/11/16 10:35
 */
public class CheckUtils {

    /**
     * 检测用户session是否过期
     * @param request request
     * @param result  result
     * @return true表示过期，false表示未过期
     */
    public static boolean userSessionTimeOut(HttpServletRequest request, Result result) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            result.setMsg("用户为空");
            result.setCode(ResultCode.USER_SESSION_ERROR);
            return true;
        }
        else {
            return  false;
        }
    }
}
