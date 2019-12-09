package com.blog.www.service;

import com.blog.www.model.Comment;
import com.blog.www.model.Reply;

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
}
