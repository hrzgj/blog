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
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyu
 * @date: 2019/11/12 17:27
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
    @PostMapping("/addPassage")
    public Result addPassage(@RequestBody Blog blog, HttpServletRequest request) {
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


    @PostMapping("/deleteBlog")
    public Result deleteBlog(@RequestBody Blog blog,HttpServletRequest request){
        Result result=new Result();
        blogService.deleteBlog(blog);
        return result;
    }

}
