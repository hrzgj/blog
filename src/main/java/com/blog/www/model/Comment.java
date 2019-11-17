package com.blog.www.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author liuyanxiang
 * @Date 2019/11/3 15:43
 */
@Data
public class Comment  implements Serializable {

    /**
     * 评论id
     */
    private int id;

    /**
     *评论内容
     */
    private String content;

    /**
     * 评论的用户的id
     */
    private User user;

    /**
     * 评论的这篇博客的id
     */
    private Blog blog;

    /**
     * 评论的时间
     */
    private String time;


}
