package com.blog.www.model;

import lombok.Data;

/**
 * @author: chenyu
 * @date: 2019/11/12 15:27
 */
@Data
public class ResultCode {



    //处理成功的状态码

    public static final int SUCCESS=1;

    //处理未知错误的状态码
    public static final int UNSPECIFIED=500;

    //用户账户存在状态码
    public static final int ACCOUNT_EXIT=2;

    //用户邮箱存在状态码
    public static final int MAIL_EXIT=3;

    //用户账户密码不匹配,用于登录和修改密码
    public static final int PASSWORD_ERROR=4;

    //用户修改code的值注册失败
    public static final int REGISTER_ERROR=5;

    //用户修改url的code导致注册失败
    public static final int REGISTER_ERROR2=6;

    //用户登录session失效，登录时间过长
    public static final int USER_SESSION_ERROR=7;

    //用户修改密码的验证码错误
    public static final int IDENTITY_ERROR=8;

    //用户邮箱未注册或不存在
    public  static final int MAIL_UN_EXIT = 9;

    //用户注册发送邮件失败
    //用户注册发送邮件失败，两种情况，邮箱输入错误or该邮箱未注册
    public static final int MAIL_SEND_ERROR=9;

    //删除服务器图片失败
    public static final int DELETE_ERROR=10;

    //输入值为空
    public  static final int OBJECT_NULL = 11;

}
