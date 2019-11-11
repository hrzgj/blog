package com.blog.www.service.impl;

import com.blog.www.mapper.UserMapper;
import com.blog.www.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: chenyu
 * @date: 2019/11/1 12:46
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void upload(String fileName, int id) {
        userMapper.updatePhoto(fileName, id);
    }


}
