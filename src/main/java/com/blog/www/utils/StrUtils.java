package com.blog.www.utils;

/**
 * @author lyx
 * @date 2019/11/26 19:47
 */
public class StrUtils {

    /**
     * 将传入的字符串只截取前length个,并在最后加... ... 如果传入的字符串比length短，则返回原字符串
     * @param string 字符串
     * @param length 长度
     * @return 字符串
     */
    public static String get25Str(String string,int length){
        if (string.length() < length){
            //如果长度短于length，则返回原字符串
            return string;
        }else{
            String str = string.substring(0,length)+"... ...";
            return  str;
        }
    }


}
