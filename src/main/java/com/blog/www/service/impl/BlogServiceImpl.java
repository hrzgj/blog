package com.blog.www.service.impl;

import com.blog.www.mapper.BlogMapper;
import com.blog.www.mapper.CollectMapper;
import com.blog.www.mapper.ComMapper;
import com.blog.www.model.Blog;
import com.blog.www.service.BlogService;
import com.blog.www.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: chenyu
 * @date: 2019/11/12 17:28
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    ComMapper comMapper;

    @Autowired
    CollectMapper collectMapper;

    @Override
    public boolean addPassage(Blog blog) {
        if (blog != null){
            blog.setDate(DateUtils.getDateToDate());
            if (blogMapper.insertPassage(blog)>0){
                return true;
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
    public boolean deleteBlog(Blog blog) {

        //删除所有用户收藏夹中该博客的记录
        collectMapper.deleteColAllBlogByBlogId(blog);
        collectMapper.deleteNormalColAllBlog(blog);
        //删除评论
        comMapper.deleteBlogCom(blog);
        //最后删除博客
        return blogMapper.deleteBlog(blog) != 0;

    }
}

