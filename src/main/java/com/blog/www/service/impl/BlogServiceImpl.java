package com.blog.www.service.impl;

import com.blog.www.mapper.BlogMapper;
import com.blog.www.mapper.CollectMapper;
import com.blog.www.mapper.ComMapper;
import com.blog.www.model.Blog;
import com.blog.www.model.Collect;
import com.blog.www.service.BlogService;
import com.blog.www.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

            //找到用户的草稿箱的id
            int id  = collectMapper.selectPaper(blog.getAuthor().getId());
            //如果草稿箱中没有数据，则插入新的博客内容
            if (collectMapper.selectPaperCount(id)==0){
                //新增博客到草稿箱，先存入内容，加入关联表信息
                if (blogMapper.insertBlog(blog)>0){
                    //当插入条数不为0时为成功
                    Collect collect = new Collect();
                    collect.setBlogId(blog.getId());
                    //通过用户的id得到该用户的草稿箱id
                    collect.setUserCollectId(id);
                    return collectMapper.insertCollectToAuto(collect)!=0;
                }
            }else{
                //草稿箱中有数据，则进行修改操作
                //从关联表中找到草稿箱中的id
                blog.setId(blogMapper.selectBlogId(id));
               return blogMapper.updateBlog(blog)!=0;
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
        if (blogMapper.updateBlog(blog)>0){
            return  collectMapper.deleteEditCollect(blog.getId(),collectMapper.selectPaper(blog.getAuthor().getId()))!=0;
        }
        return false;
    public boolean deleteBlog(Blog blog) {

        collectMapper.deleteColAllBlogByBlogId(blog);
        comMapper.deleteBlogCom(blog);
        return blogMapper.deleteBlog(blog) != 0;

    }


    @Override
    public int selectBlogInEdit(Blog blog) {
        return blogMapper.selectBlogId(collectMapper.selectPaper(blog.getAuthor().getId()));
    }



}

