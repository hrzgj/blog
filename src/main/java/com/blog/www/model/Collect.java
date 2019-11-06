package com.blog.www.model;

import lombok.Data;

/**
 * @author liuyanxiang
 * @date 2019/11/3 15:22
 */
@Data
public class Collect {

    /**
     * 集合id
     */
    private int id;

    /**
     * 分组的id
     */
    private int userCollectId;

    /**
     * 在这个分组里的博客的id
     */
    private int blogId;

}
