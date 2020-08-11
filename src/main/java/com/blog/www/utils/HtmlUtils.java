package com.blog.www.utils;

/**
 * @author lyx
 * @date 2019/12/16 19:57
 */
public class HtmlUtils {

    /**
     * 字符的转义
     * @param str 传进来的字符串
     * @return 转移后的字符串
     */
    public static String HTMLEncod(String str) {
        String getStr = str;
        getStr = getStr.replace("<", "&lt;");
        getStr = getStr.replace(">", "&gt;");
        getStr = getStr.replace("&", "and");
        getStr = getStr.replace(":", "&quot;");
        getStr = getStr.replace("%", "&nbsp;");

        return getStr;
    }


}
