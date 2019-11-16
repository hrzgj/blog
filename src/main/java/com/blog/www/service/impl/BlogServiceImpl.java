package com.blog.www.service.impl;

import com.blog.www.mapper.BlogMapper;
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
    public boolean deleteBlog(Blog blog) {
        return false;
    }
}

