package com.blog.www.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyanxiang
 * @date 2019/11/3 15:22
 */
@Data
public class Collect  implements Serializable {

    /**
     * 集合id
     */
    private Integer id;

    /**
     * 分组的id
     */
    private Integer userCollectId;

    /**
     * 在这个分组里的博客的id
     */
    private Integer blogId;

}
