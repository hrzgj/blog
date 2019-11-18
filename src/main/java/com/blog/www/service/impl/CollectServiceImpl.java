package com.blog.www.service.impl;


import com.blog.www.mapper.CollectMapper;
import com.blog.www.model.Collect;
import com.blog.www.model.UserCollect;
import com.blog.www.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
<<<<<<< HEAD
 * @author lyx
 * @date 2019/11/16 16:35
=======
 * @author: chenyu
 * @date: 2019/11/16 20:13
>>>>>>> 263a0bb915e25c9230ff256db8733c0ecfb3504e
 */
@Service
public class CollectServiceImpl implements CollectService{

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


    @Override
    public boolean insertCollect(Collect collect) {
        if (collectMapper.insertCollect(collect)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean insertCollectToAuto(Collect collect) {
        if (collectMapper.insertCollectToAuto(collect)>0){
            return true;
        }
        return false;
    }

}
