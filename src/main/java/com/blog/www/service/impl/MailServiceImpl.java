package com.blog.www.service.impl;

import com.blog.www.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;


import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author: chenyu
 * @date: 2019/10/30 21:35
 */
@Service
public class MailServiceImpl implements MailService {

    private final Logger logger=LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void sendMail(String to, String subject, String content) {
        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=null;
        try {
            helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setTo(subject);
            helper.setText(content,true);
            mailSender.send(message);
            logger.info("邮件已经发送");
        } catch (MessagingException e) {
            logger.error("发生邮件出错了！",e);
        }

    }


}
