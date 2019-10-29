package com.blog.www.model;

import lombok.Data;

/**
 * @author: chenyu
 * @date: 2019/10/29 21:41
 */
@Data
public class Result<T> {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private  T data;
}
