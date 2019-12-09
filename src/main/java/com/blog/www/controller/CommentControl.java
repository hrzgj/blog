package com.blog.www.controller;

import com.blog.www.model.*;
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
        if(comment.getBlog()==null||comment.getContent()==null){
            result.setMsg("传参为空");
            result.setCode(ResultCode.OBJECT_NULL);
            return result;
        }
        User user= (User) request.getSession().getAttribute("user");
//        comment.setUser(user.getId());
        int flag =comService.addComment(comment);
        if(flag==ResultCode.SUCCESS){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("新增成功");
            return result;
        }
        else if(flag==ResultCode.BLOG_NOT_EXIT){
            result.setMsg("博客不存在，无法评论");
            result.setCode(ResultCode.BLOG_NOT_EXIT);
            return result;
        }
        else {
            result.setCode(ResultCode.UNSPECIFIED);
            result.setMsg("新增失败");
            return result;
        }


    }

    @PostMapping("/replyComment")
    public Result replyComment(@RequestBody Reply reply, HttpServletRequest request){
        Result result=new Result();
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        if(reply.getBeReply()==null||reply.getContent()==null||reply.getBlogId()==null||reply.getCommentId()==null){
            result.setMsg("传参为空");
            result.setCode(ResultCode.OBJECT_NULL);
            return result;
        }
        int flag=comService.replyComment(reply);
        result.setCode(flag);
        if(flag==ResultCode.SUCCESS){
            result.setMsg("回复成功");
        }
        else if(flag==ResultCode.BLOG_NOT_EXIT){
            result.setMsg("博客不存在");
        }
        else if(flag==ResultCode.COMMENT_NO_EXIT){
            result.setMsg("评论不存在");
        }
        else {
            result.setMsg("回复失败");
        }
        return result;
    }
}
