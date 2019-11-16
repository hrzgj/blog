package com.blog.www.controller;

import com.blog.www.model.Blog;
import com.blog.www.model.Result;
import com.blog.www.model.ResultCode;
import com.blog.www.model.User;
import com.blog.www.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
<<<<<<< HEAD
 * @author lyx
 * @date 2019/11/16 10:21
 */
@RestController
public class BlogControl {

    @Autowired
    BlogService blogService;


    @PostMapping("/addPassage")
    public Result addPassage(@RequestBody Blog blog, HttpServletRequest request) {
        Result<Blog> result = new Result<>();
        User user = (User) request.getSession().getAttribute("user");
        blog.setAuthor(user);
        if (blog == null) {
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("输入博客内容为空");
        } else {
            if (blogService.addPassage(blog)) {
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

}
