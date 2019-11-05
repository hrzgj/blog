package com.blog.www.utils;

import java.util.UUID;

/**
 * 产生一串随机字符串
 * @author: chenyu
 * @date: 2019/10/30 21:25
 */
public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }


}
