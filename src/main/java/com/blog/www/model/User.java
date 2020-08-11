package com.blog.www.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenyu
 */
@Data
public class User  implements Serializable {

    /**
     * 用户id
     */
    private Integer id;

    /**
     *用户账户
     */
    private String account;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户头像地址
     */
    private String photo;

    /**
     *用户邮箱
     */
    private String mail;

    /**
     *用户状态，1是注册，0是未点击链接注册
     */
    private Integer status;
}
