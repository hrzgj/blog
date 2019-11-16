package com.blog.www.service.impl;


import com.blog.www.mapper.CollectMapper;
import com.blog.www.model.Collect;
import com.blog.www.model.UserCollect;
import com.blog.www.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: chenyu
 * @date: 2019/11/16 20:13
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    CollectMapper collectMapper;

    @Override
    public boolean changeCollect(Collect collect) {
        return collectMapper.changeBlogCollect(collect) > 0;
    }

    @Override
    public boolean insertUserCollect(UserCollect userCollect) {
        return collectMapper.insertUserCollect(userCollect) > 0;
    }
}
