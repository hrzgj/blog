package com.blog.www.service.impl;

import com.blog.www.model.Blog;
import com.blog.www.service.BlogService;
import org.springframework.stereotype.Service;

/**
 * @author: chenyu
 * @date: 2019/11/12 17:28
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Override
    public boolean deleteBlog(Blog blog) {
        return false;
    }
}
