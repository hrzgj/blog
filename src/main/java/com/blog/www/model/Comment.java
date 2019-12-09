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
    private Integer id;

    /**
     *评论内容
     */
    private String content;

    /**
     * 评论的用户
     */
    private User commenter;

    /**
     * 评论的这篇博客的id
     */
    private Integer blog;

    /**
     * 评论的时间
     */
    private String time;


}
