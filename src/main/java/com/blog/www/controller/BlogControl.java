package com.blog.www.controller;

import com.blog.www.mapper.BlogMapper;
import com.blog.www.model.*;
import com.blog.www.mapper.BlogMapper;
import com.blog.www.mapper.CollectMapper;
import com.blog.www.model.*;
import com.blog.www.service.BlogService;
import com.blog.www.service.CollectService;
import com.blog.www.utils.CheckUtils;
import com.blog.www.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lyx
 * @date 2019/11/16 10:21
 */
@CrossOrigin
@RestController
public class BlogControl {

    @Autowired
    BlogService blogService;

    @Autowired
    CollectService collectService;

    /**
     * 新增博客
     * @param blog 博客内容
     * @return 成功则返回对象，失败只返回信息
     */
    @PostMapping("/addBlog")
    public Result addBlog(@RequestBody Blog blog, HttpServletRequest request) {
        Result<Blog> result = new Result<>();
        //检查session
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        User user = (User) request.getSession().getAttribute("user");
        blog.setAuthor(user);
        if (blog == null) {
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("输入博客内容为空");
        } else {
                if (blogService.addBlog(blog)) {
                    result.setCode(ResultCode.SUCCESS);
                    result.setMsg("新增博客成功");
                    result.setData(blog);
                } else {
                    result.setCode(ResultCode.UNSPECIFIED);
                    result.setMsg("新增博客失败");
                }
        }
        return result;
    }


    /**
     * 用户删除博客，同时删除博客评论，所以用户收藏夹的该博客
     * @param blog 博客
     * @param request 获取登录用户信息
     * @return  结果
     */
    @PostMapping("/deleteBlog")
    public Result deleteBlog(@RequestBody Blog blog,HttpServletRequest request){
        Result result=new Result();
        blogService.deleteBlog(blog);
        return result;
    }

    /**
     * 编辑博客，即没保存，将其存入草稿箱中
     * @param blog 博客
     * @return 结果
     */
    @PostMapping("/editBlog")
    public Result editBlog(@RequestBody Blog blog,HttpServletRequest request){
        Result<Blog> result = new Result<>();
        //检查session
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        User user = (User) request.getSession().getAttribute("user");
        blog.setAuthor(user);
        if (blog == null) {
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("输入博客内容为空");
        } else {
            if (blogService.editBlog(blog)) {
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("编辑博客成功，存入草稿箱");
                result.setData(blog);
            } else {
                result.setCode(ResultCode.UNSPECIFIED);
                result.setMsg("编辑博客失败，为存入草稿箱");
            }
        }
        return result;
    }

    /**
     * 将草稿箱中的博客保存，并没有插入收藏夹，即在草稿箱中将其删除
     * @param blog 博客内容
     * @return
     */
    @PostMapping("/addBlogInEdit")
    public Result addBlogInEdit(@RequestBody Blog blog,HttpServletRequest request){
        Result result = new Result();
        //检查session
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        User user = (User) request.getSession().getAttribute("user");
        blog.setAuthor(user);
        if (blogService.selectBlogInEdit(blog)>0){
            if (blogService.addBlogInEdit(blog)){
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("保存草稿成功");
                result.setData(blog);
            }else{
                result.setCode(ResultCode.UNSPECIFIED);
                result.setMsg("保存草稿失败");
            }
        }else{
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("草稿箱中查不到这篇博客");
        }

        return result;
    }
        //判断session
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        User user= (User) request.getSession().getAttribute("user");
        if(user.getId()!=blog.getAuthor().getId()){
            result.setMsg("用户没有权限");
            result.setCode(ResultCode.RIGHT_ERROR);
            return result;
        }
        if(blogService.deleteBlog(blog)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("删除成功，将博客评论，收藏夹收藏博客记录删除");
            return result;
        }else {
            result.setCode(ResultCode.UNSPECIFIED);
            result.setMsg("删除失败,可能该博客不存在");
            return result;
        }
    }

    /**
     * 用户修改某一篇博客
     * @param blog 博客
     * @param request 获取登录用户信息
     * @return  结果
     */
    @PostMapping("/updateBlog")
    public Result updateBlog(@RequestBody Blog blog,HttpServletRequest request){
        Result result=new Result();
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        User user= (User) request.getSession().getAttribute("user");
        if(user.getId()!=blog.getAuthor().getId()){
            result.setMsg("用户权限错误");
            result.setCode(ResultCode.RIGHT_ERROR);
            return result;
        }
        if(blogService.updateBlog(blog)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("博客更新成功");
            return result;
        }else {
            result.setMsg("博客更新失败");
            result.setCode(ResultCode.UNSPECIFIED);
            return result;
        }

}
