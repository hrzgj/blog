package com.blog.www.model;

import lombok.Data;

/**
 * @author: chenyu
 * @date: 2019/10/29 20:57
 */
@Data
public class Blog {

    /**
     * 博客id
     */
    private int id;

    /**
     * 作者
     */
    private User author;

    /**
     * 博客题目
     */
    private String title;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 博客最后修改时间
     */
    private String date;


}
