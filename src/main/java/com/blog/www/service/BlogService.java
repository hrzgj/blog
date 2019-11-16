package com.blog.www.service;

import com.blog.www.model.Blog;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: chenyu
 * @date: 2019/11/12 17:28
 */
public interface BlogService {

    @Transactional
    boolean deleteBlog(Blog blog);
}
