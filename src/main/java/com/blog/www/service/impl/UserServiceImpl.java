package com.blog.www.service.impl;

import com.blog.www.mapper.UserMapper;
import com.blog.www.model.User;
import com.blog.www.service.MailService;
import com.blog.www.service.UserService;
import com.utils.MD5Utils;
import com.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author chenyu
        */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private MailService mailService;

    @Override
    public boolean insert(User user) {
        user.setPassword(MD5Utils.encode(user.getPassword()));
        String code= UUIDUtils.getUUID();
        userMapper.insertUser(user);
        userMapper.insertCode(code,user.getId());
        String subject="验证你的邮箱";
        String context="尊敬的"+user.getUserName()+"你好"+
                "<a href=\"/checkCode?code="+code+"\">激活请点击:"+code+"</a>";
        mailService.sendMail(user.getMail(),subject,context);
        return true;
    }

    @Override
    public String findByAccountAndPassword(User user) {
        user.setPassword(MD5Utils.encode(user.getPassword()));
        user= userMapper.findByAccountAndPassword(user);
        if(user==null) {
            return "0";
        } else {
            return "1";
        }
    }

    @Override
    public boolean accountAndMailExist(User user) {
        user=userMapper.findByAccountOrMail(user);
        return user != null;
    }

    @Override
    public boolean findCode(String code) {
        int id=userMapper.findCode(code);
        if(id!=0) {
            userMapper.updateStatus(id);
            return true;
        }
        else {
            return false;
        }
    }
}
