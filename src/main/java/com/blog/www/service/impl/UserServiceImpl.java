package com.blog.www.service.impl;

import com.blog.www.mapper.UserMapper;
import com.blog.www.model.User;
import com.blog.www.service.MailService;
import com.blog.www.service.UserService;
import com.blog.www.utils.MD5Utils;
import com.blog.www.utils.UUIDUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


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
        userMapper.insertCode(user.getId(),code);
        String subject="验证你的邮箱";
        String context="尊敬的"+user.getName()+"你好"+
                "点击该链接进行注册"+
                " http://localhost:8080/checkCode?code="+code;
        mailService.sendMail(user.getMail(),subject,context);
        return true;
    }

    @Override
    public User findByAccountAndPassword(User user) {
        user.setPassword(MD5Utils.encode(user.getPassword()));
        return userMapper.findByAccountAndPassword(user);

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
            userMapper.deleteCode(id);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean updatePassword( User user,  String newPassword) {
        int count = userMapper.updatePassword(user.getId(),newPassword);
        if (count>0){
            return true;
        }
        return false;
    }
}
