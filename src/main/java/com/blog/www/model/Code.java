package com.blog.www.model;

import lombok.Data;

/**
 * @author liuyanxiang
 * @date  2019/11/03 15:20
 */
@Data
public class Code {

    /**
     * 编码id，与用户的id为同一个值
     */
    private int id;

    /**
     * 编码
     */
    private String code;

}
