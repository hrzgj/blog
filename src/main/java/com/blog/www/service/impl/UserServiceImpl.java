package com.blog.www.service.impl;

import com.blog.www.mapper.UserMapper;
import com.blog.www.model.User;
import com.blog.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }
}
