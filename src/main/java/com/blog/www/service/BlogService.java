package com.blog.www.service;

import com.blog.www.model.Blog;
import com.blog.www.model.UserCollect;
import com.github.pagehelper.Page;
import com.blog.www.model.UserCollect;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional(rollbackFor = Exception.class)
    boolean deleteBlog(Blog blog);

    /**
     * 增加博客,只是插入博客表中，未存入关联的收藏夹中
     * @param blog 博客
     * @return 成功与否
     */
    boolean addBlog(Blog blog);

    /**
     * 编辑博客存到草稿箱
     * @param blog 博客内容
     * @return 成功与否
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    boolean editBlog(Blog blog);


    /**
     * 更新博客
     * @param blog 博客
     * @return 成功与否
     */
    boolean updateBlog(Blog blog);

    /**
     * 从草稿箱中保存博客,先更新博客内容，再删除草稿箱中的博客
     * @param blog 博客
     * @return 成功与否
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    boolean addBlogInEdit(Blog blog);


    /**
     * 找到草稿箱中的你博客id
     * @param blog 博客
     * @return 数据条数
     */
    int selectBlogInEdit(Blog blog);


    /**
     * 通过分组id找到全部的博客
     * @param collectId 该分组的id
     * @return  博客列表
     */
    List<Blog> findBlogInCollect(int collectId);

    /**
     * 通过用户id找到默认收藏夹全部的博客
     * @param userCollect 该收藏夹
     * @return  博客列表
     */
    List<Blog> findBlogInAuto(UserCollect userCollect);


    /**
     * 通过博客id获取博客内容
     * @param blogId 博客id
     * @return 博客内容
     */
    Blog getBlogById(int blogId);


    /**
     * 首页博客查询，分页查询
     * @return 每页的博客
     */
    Page<Blog> findPageBlog();


}
