package com.blog.www.utils;

import java.util.Random;

/**
 * 生成6位的随机验证码
 * @author liuyanxiang
 */
public class RandomCodeUtils {


    public static String getRandomCode(){
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            //每次随机出一个数字（0-9）
            int r = random.nextInt(10);
            //把每次随机出的数字拼在一起
            code = code + r;
        }
        return code;
    }
}
