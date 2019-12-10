package com.blog.www.service.impl;

import com.blog.www.mapper.BlogMapper;
import com.blog.www.mapper.CollectMapper;
import com.blog.www.mapper.ComMapper;
import com.blog.www.mapper.UserMapper;
import com.blog.www.model.*;
import com.blog.www.service.BlogService;
import com.blog.www.utils.DateUtils;
import com.blog.www.utils.StrUtils;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyx
 * @date 2019/11/16 10:06
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    ComMapper comMapper;

    @Autowired
    CollectMapper collectMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean addBlog(Blog blog) {
        if (blog != null){
            blog.setDate(DateUtils.getDateToSecond());
            if (blogMapper.insertBlog(blog)>0){
                return true;
            }
        }
        return false;
    }



    @Override
    public boolean editBlog(Blog blog) {
        if (blog != null){
            blog.setDate(DateUtils.getDateToSecond());

            //如果传入博客id为空，说明这篇博客之前未存过
            if (blog.getId()==null){
                //新增博客到草稿箱，存入内容
                return blogMapper.insertEditBlog(blog)>0;
            }else{
                //有数据，则进行修改操作
               return blogMapper.updateEditBlog(blog)!=0;
            }
        }
        return false;
    }

    @Override
    public boolean updateBlog(Blog blog) {

        blog.setDate(DateUtils.getDateToSecond());
        return blogMapper.updateBlog(blog) != 0;
    }

    @Override
    public boolean addBlogInEdit(Blog blog) {
        //删除草稿箱中博客
        if (blogMapper.deleteEditBlog(blog.getId()) > 0 ){
            blog.setId(null);
            blog.setDate(DateUtils.getDateToSecond());
            return  blogMapper.insertBlog(blog)>0;
        }
        return false;
    }

    @Override
    public int deleteBlog(Blog blog) {

        if(blogMapper.findBlogById(blog.getId())==0){
            return ResultCode.BLOG_NOT_EXIT;
        }
        //删除所有用户收藏夹中该博客的记录
        collectMapper.deleteColAllBlogByBlogId(blog);
        collectMapper.deleteNormalColAllBlog(blog);

        //找出所有评论的id
        List<Integer> list=comMapper.findCommentIdByBlogId(blog.getId());
        //如果有评论
        if(list.size()>0) {
            //删除评论的所有回复
            comMapper.deleteRelyByListCommentId(list);
            //删除评论
            comMapper.deleteBlogCom(blog);
        }
        //最后删除博客
        if(blogMapper.deleteBlog(blog) != 0){
            return ResultCode.SUCCESS;
        }
        else {
            return ResultCode.UNSPECIFIED;
        }
    }


    @Override
    public int selectBlogInEdit(Blog blog) {
        return blogMapper.selectBlogCount(blog.getId());
    }

    @Override
    public List<Blog> findBlogInCollect(int collectId) {
        List<Blog> blogs = blogMapper.findBlogInCollect(collectId);
        for (Blog blog:blogs) {
            String content = StrUtils.getStr(blog.getContent(),50);
            blog.setContent(content);
        }
        return blogs;
    }

    @Override
    public List<Blog> findBlogInAuto(int userId) {
        int DId = collectMapper.selectAuto(userId);
        if (DId != 0){
            List<Blog> blogs = blogMapper.findBlogInAuto(DId);
            for (Blog blog:blogs) {
                String content = StrUtils.getStr(blog.getContent(),50);
                blog.setContent(content);
            }
            return blogs;
        }
        return  null;
    }

    @Override
    public List<Blog> findBlogInEdit(int userId) {

        if ( userId != 0){
            List<Blog> blogs = blogMapper.findBlogInEdit(userId);
            for (Blog blog:blogs) {
                String content = StrUtils.getStr(blog.getContent(),50);
                blog.setContent(content);
            }
            return blogs;
        }
        return  null;
    }

    @Override
    public boolean deleteEditBlog(Blog blog) {
        //删除博客
        return blogMapper.deleteEditBlog(blog.getId()) != 0;
    }

    @Override
    public List<Blog> findBlogByUser(int userId) {
        if (userMapper.findUserById(userId)==null){
            return null;
        }else {
            List<Blog> blogs = blogMapper.findBlogByUser(userId);
            for (Blog blog:blogs) {
                String content = StrUtils.getStr(blog.getContent(),50);
                blog.setContent(content);
            }
            return blogs;
        }
    }

    @Override
    public Blog getBlogById(int blogId) {
        if (blogId == 0){
            return null;
        }else{
            return  blogMapper.getBlogById(blogId);
        }
    }

    @Override
    public Blog getEditBlogById(int blogId) {
        if (blogId == 0){
            return null;
        }else{
            return  blogMapper.getEditBlogById(blogId);
        }
    }

    @Override
    public Page<Blog> findPageBlog() {
        return blogMapper.findPageBlog();
    }

    @Override
    public Page<Blog> seekBlog(String seek) {
        return blogMapper.seekBlog(seek);
    }


}

