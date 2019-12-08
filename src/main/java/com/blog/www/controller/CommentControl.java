package com.blog.www.controller;

import com.blog.www.model.Comment;
import com.blog.www.model.Result;
import com.blog.www.model.ResultCode;
import com.blog.www.model.User;
import com.blog.www.service.ComService;
import com.blog.www.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyu
 * @date: 2019/12/8 22:29
 */
@RestController
@CrossOrigin
public class CommentControl {

    @Autowired
    private ComService comService;

    /**
     *@author chenyu
     *@date 22:54 2019/12/8
     *@param comment 内容
     * @param request request
     *@return 结果
     **/
    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment, HttpServletRequest request){
        Result result=new Result();
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        User user= (User) request.getSession().getAttribute("user");
        comment.setUser(user.getId());
        if(comService.addComment(comment)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("新增成功");
            return result;
        }else {
            result.setCode(ResultCode.UNSPECIFIED);
            result.setMsg("新增失败");
            return result;
        }


    }
}
