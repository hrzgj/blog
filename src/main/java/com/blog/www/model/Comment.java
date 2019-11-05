package com.blog.www.model;

import lombok.Data;

/**
 * @Author liuyanxiang
 * @Date 2019/11/3 15:43
 */
@Data
public class Comment {

    /**
     * 评论id
     */
    private int id;

    /**
     *评论内容
     */
    private String comtent;

    /**
     * 评论的用户的id
     */
    private int userId;

    /**
     * 评论的这篇博客的id
     */
    private int blogId;

    /**
     * 评论的时间
     */
    private String time;


}
