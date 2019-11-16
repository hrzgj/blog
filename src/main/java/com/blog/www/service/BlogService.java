package com.blog.www.service;

import com.blog.www.model.Blog;
import org.springframework.transaction.annotation.Transactional;

import com.blog.www.model.Blog;

/**
 * @author: chenyu
 * @date: 2019/11/12 17:28
 */
public interface BlogService {

    /**
     * 删除博客，同时删除所以用户收藏夹里的博客，和该博客的评论
     * @param blog 博客
     * @return 成功与否
     */
    @Transactional
    boolean deleteBlog(Blog blog);

    /**
     * 增加博客
     * @param blog 博客
     * @return 成功与否
     */
    boolean addPassage(Blog blog);


    /**
     * 更新博客
     * @param blog 博客
     * @return 成功与否
     */
    boolean updateBlog(Blog blog);


}
