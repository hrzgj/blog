package com.blog.www.service;

import com.blog.www.model.Comment;
import com.blog.www.model.Reply;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: chenyu
 * @date: 2019/12/8 22:36
 */
public interface ComService {

    /**
     * 新增评论
     *@author chenyu
     *@date 22:54 2019/12/8
     *@param comment 内容
     *@return 结果
     **/
    int addComment(Comment comment);

    /**
     * 新增评论回复
     *@author chenyu
     *@date 22:08 2019/12/9
     *@param reply 回复
     *@return 结果
     **/
    int replyComment(Reply reply);

    /**
     * 删除评论，同时删除该评论下的所有回复
     *@author chenyu
     *@date 22:08 2019/12/9
     *@param comment 评论
     *@return 结果
     **/
    @Transactional
    int deleteComment(Comment comment);

    /**
     * 删除回复
     *@author chenyu
     *@date 22:09 2019/12/9
     *@param reply 回复
     *@return 结果
     **/
    boolean deleteReply(Reply reply);
}
