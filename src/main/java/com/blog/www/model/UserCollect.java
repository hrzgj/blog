package com.blog.www.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author liuyanxiang
 * @Date 2019/11/3 15:40
 */
@Data
public class UserCollect implements Serializable {

    /**
     *分组的id
     */
    private Integer id;

    /**
     * 此分组的所有者user的id
     */
    private Integer userId;

    /**
     * 此分组的名字
     */
    private String name;

    /**
     * 分组简介
     */
    private String intro;

}
