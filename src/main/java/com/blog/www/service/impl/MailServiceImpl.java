package com.blog.www.service.impl;

import com.blog.www.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;



import org.springframework.stereotype.Service;



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
    public boolean sendMail(String to, String subject, String content) {
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
        try{
            mailSender.send(message);
        }catch (MailException e){
            return false;
        }

        return true;

    }


}
