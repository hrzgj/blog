package com.blog.www.service.impl;

import com.blog.www.mapper.UserMapper;
import com.blog.www.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: chenyu
 * @date: 2019/11/1 12:46
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void upload(String fileName,int id) {
            userMapper.updtaePhoto(fileName,id);
    }
}
