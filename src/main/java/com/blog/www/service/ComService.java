package com.blog.www.service;

import com.blog.www.model.Comment;
import com.blog.www.model.Reply;

import java.util.List;

/**
 * @author: chenyu
 * @date: 2019/12/8 22:36
 */
public interface ComService {

    /**
     *@author chenyu
     *@date 22:55 2019/12/8
     *@param comment 内容
     *@return 是否成功
     **/
    int addComment(Comment comment);

    int replyComment(Reply reply);

    /**
     * 通过博客id获得该博客的评论列表
     * @param blogId 博客id
     * @return 评论列表
     */
    List<Comment> getCommentsByBlogId(Integer blogId);

}
