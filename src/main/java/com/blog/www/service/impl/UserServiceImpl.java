package com.blog.www.service.impl;

import com.blog.www.mapper.UserMapper;
import com.blog.www.model.User;
import com.blog.www.service.UserService;
import com.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author chenyu
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public String insert(User user) {
        user.setPassword(MD5Utils.encode(user.getPassword()));
        return userMapper.insert(user);
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
}
