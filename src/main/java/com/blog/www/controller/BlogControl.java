package com.blog.www.controller;

import com.blog.www.model.*;
import com.blog.www.service.BlogService;
import com.blog.www.service.CollectService;
import com.blog.www.service.ComService;
import com.blog.www.utils.CheckUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lyx
 * @date 2019/11/16 10:21
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class BlogControl {

    @Autowired
    BlogService blogService;

    @Autowired
    CollectService collectService;

    @Autowired
    ComService comService;

    /**
     * 新增博客
     * @param blog 博客内容
     * @return 成功则返回对象，失败只返回信息
     */
    @PostMapping("/addBlog")
    public Result<Blog> addBlog(@RequestBody Blog blog, HttpServletRequest request) {
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
        //判断session
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        User user= (User) request.getSession().getAttribute("user");
        if(!user.getId().equals(blog.getAuthor().getId())){
            result.setMsg("用户没有权限");
            result.setCode(ResultCode.RIGHT_ERROR);
            return result;
        }
        int flag=blogService.deleteBlog(blog);
        result.setCode(flag);
        if(flag==1){
            result.setMsg("删除成功，将博客评论，收藏夹收藏博客记录删除");
        }
        else if(flag==ResultCode.BLOG_NOT_EXIT){
            result.setMsg("博客不存在");
        }
        else {
            result.setMsg("删除失败,可能该博客不存在");
        }
        return result;
    }

    /**
     * 编辑博客，即没发表，将其存入草稿箱中
     * @param blog 博客
     * @return 结果
     */
    @PostMapping("/editBlog")
    public Result<Blog> editBlog(@RequestBody Blog blog, HttpServletRequest request){
        Result<Blog> result = new Result<>();
        //检查session
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        User user = (User) request.getSession().getAttribute("user");
        user.setPassword(null);
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
                result.setMsg("编辑博客失败，未存入草稿箱");
            }
        }
        return result;
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


    /**
     * 首页博客查询，分页查询
     * @param pageNum 页数
     * @param pageSize 每页博客数量
     * @return 结果
     */
    @GetMapping("/findPageBlog")
    public Result<PageInfo<Blog>> findPageBlog(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize){
        Result<PageInfo<Blog>> result=new Result<>();
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Blog> pageInfo=new PageInfo<>(blogService.findPageBlog());
        result.setCode(ResultCode.SUCCESS);
        result.setData(pageInfo);
        return result;
    }

    /**
     * 将草稿箱中的博客保存，并没有插入收藏夹，即在草稿箱中将其删除
     * @param blog 博客内容
     * @return 结果
     */
    @PostMapping("/addBlogInEdit")
    public Result<Blog> addBlogInEdit(@RequestBody Blog blog, HttpServletRequest request){
        Result<Blog> result = new Result<>();
        //检查session
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        User user = (User) request.getSession().getAttribute("user");
        user.setPassword(null);
        blog.setAuthor(user);
        //查询草稿箱中是否有这个博客
        if (blogService.selectBlogInEdit(blog)>0){
            if (blogService.addBlogInEdit(blog)){
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("发表此草稿成功，此草稿已删除");
                result.setData(blog);
            }else{
                result.setCode(ResultCode.UNSPECIFIED);
                result.setMsg("发表草稿失败");
            }
        }else{
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("草稿箱中查不到这篇博客");
        }

        return result;
    }

    /**
     *通过收藏夹查找非默认收藏夹里的博客
     * @param userCollect 收藏夹
     * @return 博客列表
     */
    @PostMapping("/getBlogByCollect")
    public  Result<List<Blog>> getBlogByCollect(@RequestBody UserCollect userCollect){
        Result<List<Blog>> result = new Result<>();
        if (userCollect == null){
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("对象信息为空");
        }else{
            List<Blog> list = blogService.findBlogInCollect(userCollect.getId());
            if (list != null && !list.isEmpty()){
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("查找该收藏夹的博客成功");
                result.setData(list);
            }else if (list.isEmpty()){
                result.setCode(ResultCode.NO_BLOG);
                result.setMsg("查询成功，但该用户没有写过博客");
            }else{
                result.setCode(ResultCode.UNSPECIFIED);
                result.setMsg("查找该收藏夹的博客失败");
            }
        }
        return result;
    }


    /**
     *通过收藏夹查找默认收藏夹里的博客
     * @param request 请求，用于得到登录的该用户信息
     * @return 博客列表
     */
    @GetMapping("/getBlogInAuto")
    public  Result<List<Blog>> getBlogInAuto(HttpServletRequest request){
        Result<List<Blog>> result = new Result<>();
        if (CheckUtils.userSessionTimeOut(request,result)){
            return  result;
        }else{
            User user = (User) request.getSession().getAttribute("user");
            List<Blog> list = blogService.findBlogInAuto(user.getId());
            if (list != null && !list.isEmpty()){
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("查找该默认收藏夹的博客成功");
                result.setData(list);
            }else if (list.isEmpty()){
                result.setCode(ResultCode.NO_BLOG);
                result.setMsg("查询成功，但该用户没有写过博客");
            }else{
                result.setCode(ResultCode.UNSPECIFIED);
                result.setMsg("查找该默认收藏夹的博客失败");
            }
        }
        return result;
    }

    /**
     *查找草稿箱里的博客
     * @param request 请求，用于得到登录的该用户信息
     * @return 博客列表
     */
    @GetMapping("/getBlogInEdit")
    public  Result<List<Blog>> getBlogInEdit(HttpServletRequest request){
        Result<List<Blog>> result = new Result<>();
        if (CheckUtils.userSessionTimeOut(request,result)){
            return  result;
        }else{
            User user = (User) request.getSession().getAttribute("user");
            List<Blog> list = blogService.findBlogInEdit(user.getId());
            if (list != null && !list.isEmpty()){
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("查找草稿箱的博客成功");
                result.setData(list);
            }else if (list.isEmpty()){
                result.setCode(ResultCode.NO_BLOG);
                result.setMsg("查询成功，但该用户草稿箱没有博客");
            }else{
                result.setCode(ResultCode.UNSPECIFIED);
                result.setMsg("查找草稿箱的博客失败");
            }
        }
        return result;
    }

    /**
     * 用户删除草稿箱中的博客
     * @param blog 博客
     * @param request 获取登录用户信息
     * @return  结果
     */
    @PostMapping("/deleteEditBlog")
    public Result deleteEditBlog(@RequestBody Blog blog,HttpServletRequest request){
        Result result=new Result();
        //判断session
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        User user= (User) request.getSession().getAttribute("user");
        blog.setAuthor(user);
        if(blogService.deleteEditBlog(blog)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("删除草稿成功");
            return result;
        }else {
            result.setCode(ResultCode.UNSPECIFIED);
            result.setMsg("删除失败,可能该草稿不存在");
            return result;
        }
    }

    /**
     * 获得单篇博客内容
     * @param blogId 博客id
     * @return 博客内容
     */
    @GetMapping("/getOneBlog")
    public Result<Blog> getOneBlog(@RequestParam(value="id",required = false,defaultValue = "0")  Integer blogId) {
        Result<Blog> result = new Result<Blog>();
        if (blogId == null) {
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("传输对象为空");
            return result;
        } else {
            if (blogService.getBlogById(blogId) != null) {
                Blog blog = blogService.getBlogById(blogId);
                //获得该博客的评论
                blog.setComments(comService.getCommentsByBlogId(blogId));
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("获得博客内容成功");
                result.setData(blog);
            } else {
                result.setCode(ResultCode.UNSPECIFIED);
                result.setMsg("获得博客内容失败,该博客可能不存在");
            }
        }
        return result;


    }

    /**
     * 获得单篇草稿内容
     * @param blogId 草稿id
     * @return 草稿内容
     */
    @GetMapping("/getEditBlog")
    public Result<Blog> getEditBlog(@RequestParam("id") Integer blogId){
        Result<Blog> result = new Result<Blog>();
        if (blogId == null){
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("传输对象为空");
            return result;
        }else{
            Blog blog = blogService.getEditBlogById(blogId);
            if (blog !=null){
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("获得草稿内容成功");
                result.setData(blog);
            }else{
                result.setCode(ResultCode.UNSPECIFIED);
                result.setMsg("获得草稿内容失败");
            }
        }
        return result;
    }

    /**
     * 搜索博客，分页查询
     * @param seek 搜索内容
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 结果
     */
    @GetMapping("/seekBlog")
    public Result<PageInfo<Blog>> seekBlog(@RequestParam(value ="seek" ) String seek,
                                           @RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        Result<PageInfo<Blog>> result = new Result<>();
        PageHelper.startPage(pageNum,pageSize);
        if (seek == null) {
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("传参错误");
            return result;
        }
        PageInfo<Blog> pageInfo=new PageInfo<>(blogService.seekBlog(seek));
        result.setData(pageInfo);
        result.setMsg("查询成功");
        result.setCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     *通过用户id获得博客的内容
     * @param userId 用户id
     * @return 博客列表
     */
    @GetMapping("/getBlogByUserId")
    public Result<List<Blog>> getBlogByUserId(@RequestParam(value = "userId") Integer userId){
        Result<List<Blog>> result = new Result<>();
        if (userId == null){
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("对象信息为空");
        }else{
            List<Blog> list = blogService.findBlogByUser(userId);
            if (list != null && !list.isEmpty()){
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("查找该用户的博客成功");
                result.setData(list);
            }else if (list.isEmpty()){
                result.setCode(ResultCode.NO_BLOG);
                result.setMsg("查询成功，但该用户没有写过博客");
            }else{
                result.setCode(ResultCode.UNSPECIFIED);
                result.setMsg("查找该用户的博客失败");
            }
        }
        return result;
    }



}
