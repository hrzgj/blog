package com.blog.www.service;

import com.blog.www.model.Blog;

/**
 * @author lyx
 * @date 2019/11/16 10:04
 */
public interface BlogService {

    /**
     * 增加博客
     * @param blog 博客
     * @return 成功与否
     */
    boolean addPassage(Blog blog);


}
