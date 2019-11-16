package com.blog.www.controller;

import com.blog.www.model.Blog;
import com.blog.www.model.Result;
import com.blog.www.model.User;
import com.blog.www.service.BlogService;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.session.ResultContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyu
 * @date: 2019/11/12 17:27
 */
@Controller
@CrossOrigin
public class BlogControl  {

    @Autowired
    private BlogService blogService;

    @PostMapping("/addBlog")
    public Result addBlog(@RequestBody Blog blog, HttpServletRequest request){
        Result result=new Result();
        User user= (User) request.getSession().getAttribute("user");
        blog.setAuthor(user);
        return result;
    }


    @PostMapping("/deleteBlog")
    public Result deleteBlog(@RequestBody Blog blog,HttpServletRequest request){
        Result result=new Result();
        blogService.deleteBlog(blog);
        return result;
    }

}
