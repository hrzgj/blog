package com.utils;

import java.util.UUID;

/**
 * 生成邮件验证码
 * @author: chenyu
 * @date: 2019/10/30 21:25
 */
public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
