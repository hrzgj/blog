package com.blog.www.service.impl;

import com.blog.www.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author: chenyu
 * @date: 2019/10/30 21:35
 */
@Service
public class MailServiceImpl implements MailService {


    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendMail(String to, String subject, String content) {
//        MimeMessage message=mailSender.createMimeMessage();
//        MimeMessageHelper helper=null;
//        try {
//            helper=new MimeMessageHelper(message,true);
//            helper.setFrom(from);
//            helper.setTo(to);
//            helper.setTo(subject);
//            helper.setText(content,true);
//            mailSender.send(message);
//            logger.info("邮件已经发送");
//        } catch (Exception e) {
//            logger.error("发生邮件出错了！",e);
//        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);



    }


}
