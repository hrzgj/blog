package com.blog.www.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: chenyu
 * @date: 2019/10/29 21:41
 */
@Data
@JsonIgnoreProperties(value = {"handler"})
public class Result<T> implements Serializable {
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
