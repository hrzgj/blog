package com.blog.www.service;

import com.blog.www.model.Blog;
import com.blog.www.model.User;
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
    @Transactional
    int deleteBlog(Blog blog);

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
     * 从草稿箱中保存博客,先删除草稿箱博客内容，再插入新的博客到博客表中
     * @param blog 博客
     * @return 成功与否
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    boolean addBlogInEdit(Blog blog);

    /**
     * 查询草稿箱中的博客的数量
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
     * @param userId 用户id
     * @return  博客列表
     */
    List<Blog> findBlogInAuto(int userId);

    /**
     * 通过用户id找到草稿箱全部的博客
     * @param userId 用户id
     * @return  博客列表
     */
    List<Blog> findBlogInEdit(int userId);

    /**
     * 删除草稿箱博客，同时删除所以用户收藏夹里的博客，和该博客的评论
     * @param blog 博客
     * @return 成功与否
     */
    @Transactional
    boolean deleteEditBlog(Blog blog);

    /**
     * 通过user查找该用户的
     * @param userId 用户id
     * @return 博客列表
     */
    List<Blog> findBlogByUser(int userId);


    /**
     * 通过博客id获取博客内容
     * @param blogId 博客id
     * @return 博客内容
     */
    Blog getBlogById(int blogId);

    /**
     * 通过草稿id获取草稿内容
     * @param blogId 草稿id
     * @return 草稿内容
     */
    Blog getEditBlogById(int blogId);



    /**
     * 首页博客查询，分页查询
     * @return 每页的博客
     */
    Page<Blog> findPageBlog();

    /**
     * 搜索博客，分页展示
     * @param seek 搜索内容
     * @return 结果集
     */
    Page<Blog> seekBlog(String seek);

}
