package com.blog.www.controller;

import com.blog.www.model.*;
import com.blog.www.service.ComService;
import com.blog.www.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyu
 * @date: 2019/12/8 22:29
 */
@RestController
@CrossOrigin(allowCredentials = "true")
//@RequestMapping("/api")
public class CommentControl {

    @Autowired
    private ComService comService;

    /**
     * 新增评论
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
        comment.setCommenter(user);
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

    /**
     * 新增评论回复
     *@author chenyu
     *@date 22:08 2019/12/9
     *@param reply 回复
     *@return 结果
     **/
    @PostMapping("/replyComment")
    public Result replyComment(@RequestBody Reply reply, HttpServletRequest request){
        Result result=new Result();
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        User user= (User) request.getSession().getAttribute("user");
        reply.setReply(user);
        if(reply.getBeReply().getId()==null||reply.getContent()==null||reply.getCommentId()==null){
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
        else if(flag==ResultCode.USER_NO_EXIT){
            result.setMsg("被回复用户id错误，或者用户不存在");
        }
        else if(flag==ResultCode.COMMENT_NO_EXIT){
            result.setMsg("评论不存在");
        }
        else {
            result.setMsg("回复失败");
        }
        return result;
    }

    /**
     * 删除评论，同时删除该评论下的所有回复
     *@author chenyu
     *@date 22:08 2019/12/9
     *@param comment 评论
     *@return 结果
     **/
    @PostMapping("/deleteComment")
    public Result deleteComment(@RequestBody Comment comment,HttpServletRequest request){
        Result result=new Result();
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        if(comment.getId()==null){
            result.setMsg("传参为空");
            result.setCode(ResultCode.OBJECT_NULL);
            return result;
        }
        User user= (User) request.getSession().getAttribute("user");
        if(!comment.getCommenter().getId().equals(user.getId())){
            result.setCode(ResultCode.USER_DIFFERENT);
            result.setMsg("用户权限错误");
            return result;
        }
        if(comService.deleteComment(comment)==1){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("删除成功");
            return result;
        }
        else {
            result.setMsg("删除失败");
            result.setCode(ResultCode.UNSPECIFIED);
            return result;
        }

    }

    /**
     * 删除回复
     * 可能有缺陷
     *@author chenyu
     *@date 22:09 2019/12/9
     *@param reply 回复
     *@return 结果
     **/
    @PostMapping("/deleteReply")
    public Result deleteReply(@RequestBody Reply reply){
        Result result=new Result();
        if(reply.getId()==null){
            result.setMsg("传参为空");
            result.setCode(ResultCode.OBJECT_NULL);
            return result;
        }
        if(comService.deleteReply(reply)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("删除成功");
            return result;
        }
        else {
            result.setCode(ResultCode.UNSPECIFIED);
            result.setMsg("删除失败");
            return result;
        }
    }

}
