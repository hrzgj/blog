package com.blog.www.model;

import lombok.Data;

/**
 * @author: chenyu
 * @date: 2019/12/9 20:06
 */
@Data
public class Reply {

    /**
     * 回复id
     */
    private Integer id;

    /**
     *回复内容
     */
    private String content;

    /**
     * 回复用户的id
     */
    private Integer replyId;

    /**
     * 被回复用户的id
     */
    private Integer beReplyId;

    /**
     * 评论的这篇博客的id
     */
    private Integer blogId;

    /**
     * 回复的时间
     */
    private String time;

    /**
     * 评论的id
     */
    private Integer commentId;
}
