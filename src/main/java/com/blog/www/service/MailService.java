package com.blog.www.service;

/**
 * @author: chenyu
 * @date: 2019/10/30 21:35
 */
public interface MailService {

    /**
     * @param to 发送邮箱
     * @param subject   题目
     * @param content   内容
     */
    void sendMail(String to,String subject,String content);
}
